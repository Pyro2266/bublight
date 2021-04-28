package com.github.pyro2266.bublight.controllers;

import com.github.pyro2266.bublight.service.colormodes.ColorModesService;
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
@RequestMapping(path = "/led")
@CrossOrigin(origins = "*")
public class LedController {

    private static final Logger LOG = LoggerFactory.getLogger(LedController.class);

    private final ColorModesService colorModesService;

    @Autowired
    public LedController(ColorModesService colorModesService) {
        this.colorModesService = colorModesService;
    }

    @PostMapping(path = "/start")
    public ResponseEntity start() {
        LOG.info("Request to start color loop received");
        colorModesService.startColorLoop();
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/stop")
    public ResponseEntity stop() {
        LOG.info("Request to stop color loop received");
        colorModesService.stopColorLoop();
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/setRefreshRate/{rate}")
    public ResponseEntity setRefreshRate(@PathVariable int rate) {
        LOG.info("Request to change refresh rate to {} received", rate);
        colorModesService.setRefreshRate(rate);
        return ResponseEntity.ok().build();
    }
}
