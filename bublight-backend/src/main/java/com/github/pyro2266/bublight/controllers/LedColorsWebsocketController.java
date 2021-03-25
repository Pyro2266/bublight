package com.github.pyro2266.bublight.controllers;

import com.github.pyro2266.bublight.websocket.LedColorsWebsocketService;
import com.github.pyro2266.bublight.websocket.PressureWebsocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/ledcolor/websocket")
public class LedColorsWebsocketController {

    private static final Logger LOG = LoggerFactory.getLogger(LedColorsWebsocketController.class);

    private final LedColorsWebsocketService ledColordWebsocketService;

    @Autowired
    public LedColorsWebsocketController(LedColorsWebsocketService ledColordWebsocketService) {
        this.ledColordWebsocketService = ledColordWebsocketService;
    }

    @PostMapping(path = "/setRefreshRate/{rate}")
    public ResponseEntity setRefreshRate(@PathVariable int rate) {
        LOG.info("Request to change refresh rate of led color websocket to {} received", rate);
        ledColordWebsocketService.changeRefreshRate(rate);
        return ResponseEntity.ok().build();
    }

}
