package com.github.pyro2266.lightit.controllers;

import com.github.pyro2266.lightit.led.core.LedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/led")
public class LedController {

    private static final Logger LOG = LoggerFactory.getLogger(LedController.class);

    @Autowired
    private LedService ledService;

    @PostMapping(path = "/start")
    public ResponseEntity start() {
        LOG.info("Request to start color loop received");
        ledService.startColorLoop();
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/stop")
    public ResponseEntity stop() {
        LOG.info("Request to stop color loop received");
        ledService.stopColorLoop();
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/setRefreshRate/{rate}")
    public ResponseEntity setRefreshRate(@PathVariable int rate) {
        LOG.info("Request to change refresh rate to {} received", rate);
        ledService.setRefreshRate(rate);
        return ResponseEntity.ok().build();
    }
}
