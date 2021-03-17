package com.github.pyro2266.bublight.controllers;

import com.github.pyro2266.bublight.websocket.PressureWebsocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/pressure/websocket")
public class PressureWebsocketController {

    private static final Logger LOG = LoggerFactory.getLogger(PressureWebsocketController.class);

    private final PressureWebsocketService pressureWebsocketService;

    @Autowired
    public PressureWebsocketController(PressureWebsocketService pressureWebsocketService) {
        this.pressureWebsocketService = pressureWebsocketService;
    }

    @PostMapping(path = "/setRefreshRate/{rate}")
    public ResponseEntity setRefreshRate(@PathVariable int rate) {
        LOG.info("Request to change refresh rate of pressure websocket to {} received", rate);
        pressureWebsocketService.schedulePressureDiffNotifier(rate);
        return ResponseEntity.ok().build();
    }

}
