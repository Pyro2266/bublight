package com.github.pyro2266.bublight.controllers;

import com.github.pyro2266.bublight.service.sensor.driver.Sensor;
import com.github.pyro2266.bublight.service.sensor.driver.SensorSimulatedImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/pressure/simulated")
@ConditionalOnExpression("${bublight.simulatedPressure} or ${bublight.simulatedSensor}")
public class SimulatedPressureSensorController {

    private static final Logger LOG = LoggerFactory.getLogger(SimulatedPressureSensorController.class);

    private final SensorSimulatedImpl sensorSimulated;

    @Autowired
    public SimulatedPressureSensorController(Sensor sensor) {
        LOG.info("Starting SIMULATED pressure sensor controller...");
        if (!(sensor instanceof SensorSimulatedImpl)) {
            throw new IllegalStateException(String.format("Expected %s but got %s!", SensorSimulatedImpl.class,
                    Sensor.class));
        }
        this.sensorSimulated = (SensorSimulatedImpl) sensor;
    }

    @PostMapping(path = "/{value}")
    public ResponseEntity setSimulatedPressureValue(@PathVariable float value) {
        // set negative value - otherwise graph will be inverted
        sensorSimulated.setPressure(-value);
        return ResponseEntity.ok().build();
    }
}
