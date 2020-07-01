package com.github.pyro2266.lightit.controllers;

import com.github.pyro2266.lightit.pressure.driver.PressureSensorSimulatedImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/pressure/simulated")
@ConditionalOnProperty(value="lightit.simulatedMode", havingValue = "true")
public class SimulatedPressureSensorController {

    private static final Logger LOG = LoggerFactory.getLogger(PressureSensorSimulatedImpl.class);

    private PressureSensorSimulatedImpl pressureSensorSimulated;

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
