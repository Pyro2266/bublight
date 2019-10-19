package com.github.pyro2266.lightit.pressure.driver;

import com.github.pyro2266.lightit.led.core.LedRendererServiceSimulatedImpl;
import java.io.IOException;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PressureSensorSimulatedImpl implements PressureSensor {

    private static final Logger LOG = LoggerFactory.getLogger(LedRendererServiceSimulatedImpl.class);

    private float pressure;

    public PressureSensorSimulatedImpl(float pressure) {
        LOG.info("Starting SIMULATED pressure sensor driver...");
        this.pressure = pressure;
    }

    @Override
    public float readPressure() throws IOException, InterruptedException {
        Random random = new Random();
        return random.nextInt(1000);
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }
}
