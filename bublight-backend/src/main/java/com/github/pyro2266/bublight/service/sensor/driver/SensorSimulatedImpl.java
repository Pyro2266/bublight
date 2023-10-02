package com.github.pyro2266.bublight.service.sensor.driver;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SensorSimulatedImpl implements Sensor {

    private static final Logger LOG = LoggerFactory.getLogger(SensorSimulatedImpl.class);

    private float pressure;
    private float temperature;

    public SensorSimulatedImpl(float pressure, float temperature) {
        LOG.info("Starting SIMULATED (pressure and temperature) sensor driver...");
        this.pressure = pressure;
        this.temperature = temperature;
    }

    @Override
    public float readPressure() throws IOException, InterruptedException {
        return this.pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    @Override
    public float readTemperature() throws IOException, InterruptedException {
        return this.temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }
}
