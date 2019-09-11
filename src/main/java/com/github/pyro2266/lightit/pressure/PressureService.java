package com.github.pyro2266.lightit.pressure;

import com.github.pyro2266.lightit.drivers.BMP180;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PressureService {

    private static final Logger LOG = LoggerFactory.getLogger(PressureService.class);

    private BMP180 bmp180 = new BMP180();
    private float normalPressure = 0;
    private int durationOfCalibration;
    private int numberOfIterationsOfCalibration;

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public PressureService() {
        this(2000, 10);
    }

    public PressureService(int durationOfCalibration, int numberOfIterationsOfCalibration) {
        this.durationOfCalibration = durationOfCalibration;
        this.numberOfIterationsOfCalibration = numberOfIterationsOfCalibration;
    }

    @PostConstruct
    public void init() {
        try {
            calibrateNormalPressure();
        } catch (PressureException e) {
            LOG.warn("Unable to calibrate pressure while initializing pressure service!", e);
        }
    }

    public float getPressure() throws PressureException {
        try {
            lock.readLock().lock();
            return bmp180.readPressure();
        } catch (Exception e) {
            LOG.error("Unable to read pressure!", e);
            throw new PressureException("Unable to read pressure!", e);
        } finally {
            lock.readLock().unlock();
        }
    }

    public float getPressureDifference() throws PressureException {
        return normalPressure - getPressure();
    }

    public void calibrateNormalPressure() throws PressureException {
        float sum = 0;
        for (int i = 0; i < numberOfIterationsOfCalibration; i++) {
            try {
                float pressure = bmp180.readPressure();
                LOG.debug("Calibrating pressure. Iteration {}. Pressure {}.", i, pressure);
                sum += pressure;
                Thread.sleep(durationOfCalibration / numberOfIterationsOfCalibration);
            } catch (Exception e) {
                LOG.error("Unable to calibrate normal pressure!", e);
                throw new PressureException("Unable to calibrate normal pressure!", e);
            }
        }
        normalPressure = sum/numberOfIterationsOfCalibration;
        LOG.info("Normal pressure calibrated to {}", normalPressure);
    }

}
