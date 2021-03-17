package com.github.pyro2266.bublight.controllers;

import com.github.pyro2266.bublight.modes.ColorModesProcessor;
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

    private final ColorModesProcessor colorModesProcessor;

    @Autowired
    public LedController(ColorModesProcessor colorModesProcessor) {
        this.colorModesProcessor = colorModesProcessor;
    }

    @PostMapping(path = "/start")
    public ResponseEntity start() {
        LOG.info("Request to start color loop received");
        colorModesProcessor.startColorLoop();
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/stop")
    public ResponseEntity stop() {
        LOG.info("Request to stop color loop received");
        colorModesProcessor.stopColorLoop();
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/setRefreshRate/{rate}")
    public ResponseEntity setRefreshRate(@PathVariable int rate) {
        LOG.info("Request to change refresh rate to {} received", rate);
        colorModesProcessor.setRefreshRate(rate);
        return ResponseEntity.ok().build();
    }
}
