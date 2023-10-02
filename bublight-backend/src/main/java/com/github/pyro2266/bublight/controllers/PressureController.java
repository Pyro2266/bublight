package com.github.pyro2266.bublight.controllers;

import com.github.pyro2266.bublight.service.sensor.SensorService;
import com.github.pyro2266.bublight.service.sensor.data.SensorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/pressure")
public class PressureController {

    private static final Logger LOG = LoggerFactory.getLogger(PressureController.class);

    private final SensorService sensorService;

    @Autowired
    public PressureController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @GetMapping(path = "/")
    public ResponseEntity getPressure() {
        float pressure;
        try {
            pressure = sensorService.getPressure();
        } catch (SensorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to read pressure!");
        }
        LOG.info("Pressure is: {}", pressure);
        return ResponseEntity.ok(pressure);
    }

    @PostMapping(path = "/calibratePressure")
    public ResponseEntity calibratePressure() {
        try {
            sensorService.calibrateNormalPressure();
            return ResponseEntity.ok().build();
        } catch (SensorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to calibrate!");
        }
    }

}
