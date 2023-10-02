package com.github.pyro2266.bublight.service.sensor.driver;

import java.io.IOException;
import org.springframework.stereotype.Service;

@Service
public interface Sensor {

    /**
     * Get current pressure
     */
    float readPressure() throws IOException, InterruptedException;

    /**
     * Get current temperature
     */
    float readTemperature() throws IOException, InterruptedException;

}
