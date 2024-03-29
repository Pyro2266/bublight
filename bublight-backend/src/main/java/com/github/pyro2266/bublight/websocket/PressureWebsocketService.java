package com.github.pyro2266.bublight.websocket;

import com.github.pyro2266.bublight.service.sensor.SensorService;
import com.github.pyro2266.bublight.service.sensor.data.SensorException;
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

    private final SensorService sensorService;

    @Autowired
    public PressureWebsocketService(SimpMessagingTemplate template, SensorService sensorService) {
        super(template, DEFAULT_REFRESH_RATE);
        this.sensorService = sensorService;
    }

    @Override
    Float getData() {
        try {
            return this.sensorService.getPressureDifference();
        } catch (SensorException e) {
            LOG.error("Unable to get pressure difference!");
        }
        return 0F;
    }

    @Override
    String getEndpoint() {
        return PRESSURE_DIFF_WEBSOCKET_ENDPOINT;
    }
}
