package com.github.pyro2266.bublight.websocket;

import com.github.pyro2266.bublight.service.sensor.SensorService;
import com.github.pyro2266.bublight.service.sensor.data.SensorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class TemperatureWebsocketService extends AbstractWebsocketService<Float>{

    private static final Logger LOG = LoggerFactory.getLogger(TemperatureWebsocketService.class);

    private static final String TEMPERATURE_DIFF_WEBSOCKET_ENDPOINT = "/temperatureSubscribe";
    private static final int DEFAULT_REFRESH_RATE = 200;

    private final SensorService sensorService;

    @Autowired
    public TemperatureWebsocketService(SimpMessagingTemplate template, SensorService sensorService) {
        super(template, DEFAULT_REFRESH_RATE);
        this.sensorService = sensorService;
    }

    @Override
    Float getData() {
        try {
            return this.sensorService.getTemperature();
        } catch (SensorException e) {
            LOG.error("Unable to get temperature!");
        }
        return 0F;
    }

    @Override
    String getEndpoint() {
        return TEMPERATURE_DIFF_WEBSOCKET_ENDPOINT;
    }
}
