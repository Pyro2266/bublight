package com.github.pyro2266.lightit.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class PressureWebsocketService {

    private static final Logger LOG = LoggerFactory.getLogger(PressureWebsocketService.class);

    public final String PRESSURE_DIFF_WEBSOCKET_ENDPOINT = "/pressureSubscribe";

    private final SimpMessagingTemplate template;

    public PressureWebsocketService(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void onPressureDiffChange(float pressureDiff) {
        LOG.trace("Sending new pressure {} to websocket subscribers at {}", pressureDiff,
                PRESSURE_DIFF_WEBSOCKET_ENDPOINT);
        this.template.convertAndSend(PRESSURE_DIFF_WEBSOCKET_ENDPOINT, pressureDiff);
    }

}
