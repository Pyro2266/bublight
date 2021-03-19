package com.github.pyro2266.bublight.websocket;

import com.github.pyro2266.bublight.modes.Color;
import com.github.pyro2266.bublight.modes.ColorModesProcessor;
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
public class LedColorsWebsocketService {

    private static final Logger LOG = LoggerFactory.getLogger(LedColorsWebsocketService.class);

    private static final String COLOR_WEBSOCKET_ENDPOINT = "/colorsSubscribe";
    private static final int DEFAULT_REFRESH_RATE = 100;

    private final SimpMessagingTemplate template;
    private final ColorModesProcessor colorModesProcessor;
    private final ScheduledExecutorService executorService;

    private ScheduledFuture scheduledFuture = null;

    @Autowired
    public LedColorsWebsocketService(SimpMessagingTemplate template, ColorModesProcessor colorModesProcessor) {
        this.template = template;
        this.colorModesProcessor = colorModesProcessor;
        executorService = Executors.newSingleThreadScheduledExecutor();
    }

    @PostConstruct
    public void init() {
        scheduleColorNotifier(DEFAULT_REFRESH_RATE);
    }

    @PreDestroy
    public void tearDown() {
        executorService.shutdown();
    }

    /**
     * (Re)schedule sending current led colors to websockets. This method will (gracefully) stop any running
     * tasks and schedule new one with provided refresh rate (in milliseconds).
     */
    public synchronized void scheduleColorNotifier(int refreshRate) {
        if (scheduledFuture != null) {
            LOG.info("Stopping running led colors notifier...");
            scheduledFuture.cancel(false);
            LOG.info("Led colors notifier was stopped");
        }
        LOG.info("Scheduling new led colors notifier with refresh rate: {}", refreshRate);
        scheduledFuture = executorService.scheduleWithFixedDelay(() -> {
            Color[] colors = this.colorModesProcessor.getCurrentColors();
            LOG.trace("Sending new colors {} to websocket subscribers at {}", colors, COLOR_WEBSOCKET_ENDPOINT);
            this.template.convertAndSend(COLOR_WEBSOCKET_ENDPOINT, colors);
        }, refreshRate, refreshRate, TimeUnit.MILLISECONDS);
    }
}
