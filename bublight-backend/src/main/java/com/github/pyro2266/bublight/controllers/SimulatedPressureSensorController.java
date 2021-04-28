package com.github.pyro2266.bublight.controllers;

import com.github.pyro2266.bublight.service.pressure.driver.PressureSensorSimulatedImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/pressure/simulated")
@ConditionalOnProperty(value="bublight.simulatedMode", havingValue = "true")
public class SimulatedPressureSensorController {

    private static final Logger LOG = LoggerFactory.getLogger(SimulatedPressureSensorController.class);

    private final PressureSensorSimulatedImpl pressureSensorSimulated;

    @Autowired
    public SimulatedPressureSensorController(PressureSensorSimulatedImpl pressureSensorSimulated) {
        this.pressureSensorSimulated = pressureSensorSimulated;
        LOG.info("Starting SIMULATED pressure sensor controller...");
    }

    @PostMapping(path = "/{value}")
    public ResponseEntity setSimulatedPressureValue(@PathVariable float value) {
        // set negative value - otherwise graph will be inverted
        pressureSensorSimulated.setPressure(-value);
        return ResponseEntity.ok().build();
    }
}
