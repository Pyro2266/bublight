package com.github.pyro2266.bublight.websocket;

import com.github.pyro2266.bublight.pressure.PressureException;
import com.github.pyro2266.bublight.pressure.PressureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class PressureWebsocketService extends AbstractWebsocketService<Float>{

    private static final Logger LOG = LoggerFactory.getLogger(PressureWebsocketService.class);

    private static final String PRESSURE_DIFF_WEBSOCKET_ENDPOINT = "/pressureSubscribe";
    private static final int DEFAULT_REFRESH_RATE = 200;

    private final PressureService pressureService;

    @Autowired
    public PressureWebsocketService(SimpMessagingTemplate template, PressureService pressureService) {
        super(template, DEFAULT_REFRESH_RATE);
        this.pressureService = pressureService;
    }

    @Override
    Float getData() {
        try {
            return this.pressureService.getPressureDifference();
        } catch (PressureException e) {
            LOG.error("Unable to get pressure difference!");
        }
        return 0F;
    }

    @Override
    String getEndpoint() {
        return PRESSURE_DIFF_WEBSOCKET_ENDPOINT;
    }
}
