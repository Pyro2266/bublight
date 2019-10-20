package com.github.pyro2266.lightit.pressure;

import com.github.pyro2266.lightit.pressure.driver.PressureSensor;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class PressureServiceImpl implements PressureService {

    private static final Logger LOG = LoggerFactory.getLogger(PressureServiceImpl.class);
    private static final int DURATION_OF_CALIBRATION = 2000;
    private static final int NUMBER_OF_ITERATIONS_OF_CALIBRATION = 10;

    private PressureSensor pressureSensor;
    private Set<OnPressureDiffReadEvent> onPressureDiffReadEvents;
    private float normalPressure = 0;

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    @Autowired
    public PressureServiceImpl(PressureSensor pressureSensor,
            Set<OnPressureDiffReadEvent> onPressureDiffReadEvents) {
        this.pressureSensor = pressureSensor;
        this.onPressureDiffReadEvents = onPressureDiffReadEvents;
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
    public float getPressure() throws PressureException {
        try {
            lock.readLock().lock();
            return pressureSensor.readPressure();
        } catch (Exception e) {
            LOG.error("Unable to read pressure!", e);
            throw new PressureException("Unable to read pressure!", e);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public float getPressureDifference() throws PressureException {
        float pressureDiff = normalPressure - getPressure();
        onPressureDiffReadEvents.forEach(onPressureDiffReadEvent -> onPressureDiffReadEvent.execute(pressureDiff));
        return pressureDiff;
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
            } catch (Exception e) {
                LOG.error("Unable to calibrate normal pressure!", e);
                throw new PressureException("Unable to calibrate normal pressure!", e);
            }
        }
        normalPressure = sum/ NUMBER_OF_ITERATIONS_OF_CALIBRATION;
        LOG.info("Normal pressure calibrated to {}", normalPressure);
    }

}
