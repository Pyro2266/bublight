package com.github.pyro2266.lightit.controllers;

import com.github.pyro2266.lightit.led.LedService;
import com.github.pyro2266.lightit.pressure.PressureException;
import com.github.pyro2266.lightit.pressure.PressureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private PressureService pressureService;

    @PostMapping(path = "/{red}/{green}/{blue}")
    public ResponseEntity turnOn(@PathVariable int red, @PathVariable int green, @PathVariable int blue) {
        ledService.renderColor(red, green, blue);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/start")
    public ResponseEntity start() {
        try {
            // TODO move
            pressureService.calibrateNormalPressure();
        } catch (PressureException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ledService.startColorLoop();
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/stop")
    public ResponseEntity stop() {
        ledService.stopColorLoop();
        return ResponseEntity.ok().build();
    }


    @PostMapping(path = "/setRefreshRate/{rate}")
    public ResponseEntity setRefreshRate(@PathVariable int rate) {
        ledService.setRefreshRate(rate);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/setRange/{range}")
    public ResponseEntity setRange(@PathVariable int range) {
        ledService.setPressureRange(range);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/setDefaultBrightness/{brightness}")
    public ResponseEntity setDefaultBrightness(@PathVariable int brightness) {
        ledService.setDefaultBrightness(brightness);
        return ResponseEntity.ok().build();
    }
}
