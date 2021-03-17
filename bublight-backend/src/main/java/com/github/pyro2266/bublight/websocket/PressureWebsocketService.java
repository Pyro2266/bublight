package com.github.pyro2266.bublight.websocket;

import com.github.pyro2266.bublight.pressure.PressureException;
import com.github.pyro2266.bublight.pressure.PressureService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class PressureWebsocketService {

    private static final Logger LOG = LoggerFactory.getLogger(PressureWebsocketService.class);

    public final String PRESSURE_DIFF_WEBSOCKET_ENDPOINT = "/pressureSubscribe";
    public final int DEFAULT_REFRESH_RATE = 200;

    private final SimpMessagingTemplate template;
    private final PressureService pressureService;
    private ScheduledExecutorService executorService;
    private ScheduledFuture scheduledFuture = null;

    @Autowired
    public PressureWebsocketService(SimpMessagingTemplate template, PressureService pressureService) {
        this.template = template;
        this.pressureService = pressureService;
        executorService = Executors.newSingleThreadScheduledExecutor();
    }

    @PostConstruct
    public void init() {
        schedulePressureDiffNotifier(DEFAULT_REFRESH_RATE);
    }

    @PreDestroy
    public void tearDown() {
        executorService.shutdown();
    }

    /**
     * (Re)schedule sending actual pressure difference to websockets. This method will (gracefully) stop any running
     * tasks and schedule new one with provided refresh rate (in milliseconds).
     */
    public synchronized void schedulePressureDiffNotifier(int refreshRate) {
        if (scheduledFuture != null) {
            LOG.info("Stopping running pressure difference notifier...");
            scheduledFuture.cancel(false);
            LOG.info("Pressure difference notifier was stopped");
        }
        LOG.info("Scheduling new pressure difference notifier");
        scheduledFuture = executorService.scheduleWithFixedDelay(() -> {
            try {
                float pressureDiff = this.pressureService.getPressureDifference();
                LOG.trace("Sending new pressure {} to websocket subscribers at {}", pressureDiff,
                        PRESSURE_DIFF_WEBSOCKET_ENDPOINT);
                this.template.convertAndSend(PRESSURE_DIFF_WEBSOCKET_ENDPOINT, pressureDiff);
            } catch (PressureException e) {
                LOG.error("Unable to get pressure difference!", e);
            }
        }, refreshRate, refreshRate, TimeUnit.MILLISECONDS);
    }
}
