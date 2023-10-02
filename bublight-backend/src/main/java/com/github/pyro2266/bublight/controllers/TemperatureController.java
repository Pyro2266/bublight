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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/temperature")
public class TemperatureController {

    private static final Logger LOG = LoggerFactory.getLogger(TemperatureController.class);

    private final SensorService sensorService;

    @Autowired
    public TemperatureController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @GetMapping(path = "/")
    public ResponseEntity getTemperature() {
        float temperature;
        try {
            temperature = sensorService.getTemperature();
        } catch (SensorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to read temperature!");
        }
        LOG.info("Temperature is: {}", temperature);
        return ResponseEntity.ok(temperature);
    }

}
