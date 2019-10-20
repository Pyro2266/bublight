package com.github.pyro2266.lightit.websocket;

import org.springframework.stereotype.Service;

@Service
public class PressureWebsocketService {

    public void onPressureDiffChange(float pressureDiff) {
        // TODO send new pressureDiff to all subscribers
    }

}
