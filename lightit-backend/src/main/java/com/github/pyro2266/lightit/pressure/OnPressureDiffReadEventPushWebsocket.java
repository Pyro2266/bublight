package com.github.pyro2266.lightit.pressure;

import com.github.pyro2266.lightit.websocket.PressureWebsocketService;

public class OnPressureDiffReadEventPushWebsocket implements OnPressureDiffReadEvent {

    private PressureWebsocketService pressureWebsocketService;

    public OnPressureDiffReadEventPushWebsocket(PressureWebsocketService pressureWebsocketService) {
        this.pressureWebsocketService = pressureWebsocketService;
    }

    @Override
    public void execute(float pressureDifference) {
        pressureWebsocketService.onPressureDiffChange(pressureDifference);
    }
}
