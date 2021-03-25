package com.github.pyro2266.bublight.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public abstract class AbstractWebsocketService<T> {
    private static final Logger LOG = LoggerFactory.getLogger(LedColorsWebsocketService.class);

    private final SimpMessagingTemplate template;
    private final ScheduledExecutorService executorService;

    private int refreshRate;

    private ScheduledFuture<?> scheduledFuture = null;

    public AbstractWebsocketService(SimpMessagingTemplate template, int refreshRate) {
        this.template = template;
        this.refreshRate = refreshRate;
        executorService = Executors.newSingleThreadScheduledExecutor();
    }

    @PreDestroy
    public void tearDown() {
        executorService.shutdown();
    }

    /**
     * (Re)schedule sending data to websockets. This method will (gracefully) stop any running
     * tasks and schedule new one with provided refresh rate (in milliseconds).
     */
    @PostConstruct
    public synchronized void scheduleNotifier() {
        if (scheduledFuture != null) {
            LOG.info("Stopping running notifier...");
            scheduledFuture.cancel(false);
            LOG.info("Notifier was stopped");
        }
        LOG.info("Scheduling new notifier with refresh rate: {}", refreshRate);
        scheduledFuture = executorService.scheduleWithFixedDelay(() -> {
            T data = getData();
            LOG.trace("Sending new data {} to websocket subscribers at {}", data, getEndpoint());
            this.template.convertAndSend(getEndpoint(), data);
        }, refreshRate, refreshRate, TimeUnit.MILLISECONDS);
    }

    public void changeRefreshRate(int refreshRate){
        this.refreshRate = refreshRate;
        scheduleNotifier();
    }

    abstract T getData();

    abstract String getEndpoint();
}
