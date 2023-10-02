package com.github.pyro2266.bublight.service.sensor.impl;

import com.github.pyro2266.bublight.service.sensor.SensorService;
import com.github.pyro2266.bublight.service.sensor.data.SensorException;
import com.github.pyro2266.bublight.service.sensor.driver.Sensor;
import java.io.IOException;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SensorServiceImpl implements SensorService {

    private static final Logger LOG = LoggerFactory.getLogger(SensorServiceImpl.class);
    private static final int DURATION_OF_CALIBRATION = 2000;
    private static final int NUMBER_OF_ITERATIONS_OF_CALIBRATION = 10;

    private final Sensor sensor;
    private float normalPressure = 0;

    @Autowired
    public SensorServiceImpl(Sensor sensor) {
        this.sensor = sensor;
    }

    @PostConstruct
    public void init() {
        try {
            calibrateNormalPressure();
        } catch (SensorException e) {
            LOG.warn("Unable to calibrate pressure while initializing pressure service!", e);
        }
    }

    @Override
    public synchronized float getPressure() throws SensorException {
        try {
            return sensor.readPressure();
        } catch (InterruptedException | IOException e) {
            LOG.error("Unable to read pressure!", e);
            throw new SensorException("Unable to read pressure!", e);
        }
    }

    @Override
    public float getPressureDifference() throws SensorException {
        return normalPressure - getPressure();
    }

    @Override
    public void calibrateNormalPressure() throws SensorException {
        float sum = 0;
        for (int i = 0; i < NUMBER_OF_ITERATIONS_OF_CALIBRATION; i++) {
            try {
                float pressure = sensor.readPressure();
                LOG.debug("Calibrating pressure. Iteration {}. Pressure {}.", i, pressure);
                sum += pressure;
                Thread.sleep(DURATION_OF_CALIBRATION / NUMBER_OF_ITERATIONS_OF_CALIBRATION);
            } catch (InterruptedException | IOException e) {
                LOG.error("Unable to calibrate normal pressure!", e);
                throw new SensorException("Unable to calibrate normal pressure!", e);
            }
        }
        normalPressure = sum / NUMBER_OF_ITERATIONS_OF_CALIBRATION;
        LOG.info("Normal pressure calibrated to {}", normalPressure);
    }

    @Override
    public float getTemperature() throws SensorException {
        try {
            return sensor.readTemperature();
        } catch (InterruptedException | IOException e) {
            LOG.error("Unable to read temperature!", e);
            throw new SensorException("Unable to read temperature!", e);
        }
    }

}
