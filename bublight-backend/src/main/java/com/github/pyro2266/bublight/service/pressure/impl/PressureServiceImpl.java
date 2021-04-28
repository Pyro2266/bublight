package com.github.pyro2266.bublight.service.pressure.impl;

import com.github.pyro2266.bublight.service.pressure.data.PressureException;
import com.github.pyro2266.bublight.service.pressure.PressureService;
import com.github.pyro2266.bublight.service.pressure.driver.PressureSensor;
import java.io.IOException;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PressureServiceImpl implements PressureService {

    private static final Logger LOG = LoggerFactory.getLogger(PressureServiceImpl.class);
    private static final int DURATION_OF_CALIBRATION = 2000;
    private static final int NUMBER_OF_ITERATIONS_OF_CALIBRATION = 10;

    private final PressureSensor pressureSensor;
    private float normalPressure = 0;

    @Autowired
    public PressureServiceImpl(PressureSensor pressureSensor) {
        this.pressureSensor = pressureSensor;
    }

    @PostConstruct
    public void init() {
        try {
            calibrateNormalPressure();
        } catch (PressureException e) {
            LOG.warn("Unable to calibrate pressure while initializing pressure service!", e);
        }
    }

    @Override
    public synchronized float getPressure() throws PressureException {
        try {
            return pressureSensor.readPressure();
        } catch (InterruptedException | IOException e) {
            LOG.error("Unable to read pressure!", e);
            throw new PressureException("Unable to read pressure!", e);
        }
    }

    @Override
    public float getPressureDifference() throws PressureException {
        return normalPressure - getPressure();
    }

    @Override
    public void calibrateNormalPressure() throws PressureException {
        float sum = 0;
        for (int i = 0; i < NUMBER_OF_ITERATIONS_OF_CALIBRATION; i++) {
            try {
                float pressure = pressureSensor.readPressure();
                LOG.debug("Calibrating pressure. Iteration {}. Pressure {}.", i, pressure);
                sum += pressure;
                Thread.sleep(DURATION_OF_CALIBRATION / NUMBER_OF_ITERATIONS_OF_CALIBRATION);
            } catch (InterruptedException | IOException e) {
                LOG.error("Unable to calibrate normal pressure!", e);
                throw new PressureException("Unable to calibrate normal pressure!", e);
            }
        }
        normalPressure = sum / NUMBER_OF_ITERATIONS_OF_CALIBRATION;
        LOG.info("Normal pressure calibrated to {}", normalPressure);
    }

}
