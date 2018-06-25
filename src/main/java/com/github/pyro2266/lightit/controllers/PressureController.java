package com.github.pyro2266.lightit.controllers;

import com.github.pyro2266.lightit.pressure.PressureException;
import com.github.pyro2266.lightit.pressure.PressureService;
import com.github.pyro2266.lightit.led.core.LedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/pressure")
public class PressureController {

    private static final Logger LOG = LoggerFactory.getLogger(PressureController.class);

    @Autowired
    private PressureService pressureService;

    @Autowired
    private LedService ledService;

    @GetMapping(path = "/")
    public ResponseEntity getPressure() {
        float pressure;
        try {
            pressure = pressureService.getPressure();
        } catch (PressureException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to read pressure!");
        }
        LOG.info("Pressure is: {}", pressure);
        return ResponseEntity.ok(pressure);
    }

    @PostMapping(path = "/calibratePressure")
    public ResponseEntity calibratePressure() {
        try {
            pressureService.calibrateNormalPressure();
            return ResponseEntity.ok().build();
        } catch (PressureException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to calibrate!");
        }
    }

}
