package com.github.pyro2266.bublight.websocket;

import com.github.pyro2266.bublight.service.colormodes.ColorModesService;
import com.github.pyro2266.bublight.service.colormodes.data.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class LedColorsWebsocketService extends AbstractWebsocketService<Color[]> {

    private static final Logger LOG = LoggerFactory.getLogger(LedColorsWebsocketService.class);

    private static final String COLOR_WEBSOCKET_ENDPOINT = "/colorsSubscribe";
    private static final int DEFAULT_REFRESH_RATE = 100;

    private final ColorModesService colorModesService;

    @Autowired
    public LedColorsWebsocketService(SimpMessagingTemplate template, ColorModesService colorModesService) {
        super(template, DEFAULT_REFRESH_RATE);
        this.colorModesService = colorModesService;
    }

    @Override
    Color[] getData() {
        return this.colorModesService.getCurrentColors();
    }

    @Override
    String getEndpoint() {
        return COLOR_WEBSOCKET_ENDPOINT;
    }
}
