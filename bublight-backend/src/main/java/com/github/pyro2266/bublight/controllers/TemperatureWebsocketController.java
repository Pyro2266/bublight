package com.github.pyro2266.bublight.controllers;

import com.github.pyro2266.bublight.websocket.TemperatureWebsocketService;
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
@RequestMapping(path = "/temperature/websocket")
public class TemperatureWebsocketController {

    private static final Logger LOG = LoggerFactory.getLogger(TemperatureWebsocketController.class);

    private final TemperatureWebsocketService temperatureWebsocketService;

    @Autowired
    public TemperatureWebsocketController(TemperatureWebsocketService temperatureWebsocketService) {
        this.temperatureWebsocketService = temperatureWebsocketService;
    }

    @PostMapping(path = "/setRefreshRate/{rate}")
    public ResponseEntity setRefreshRate(@PathVariable int rate) {
        LOG.info("Request to change refresh rate of temperature websocket to {} received", rate);
        temperatureWebsocketService.changeRefreshRate(rate);
        return ResponseEntity.ok().build();
    }

}
