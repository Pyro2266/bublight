package com.github.pyro2266.lightit.pressure;

import com.github.pyro2266.lightit.drivers.BMP180;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PressureServiceImpl implements PressureService {

    private static final Logger LOG = LoggerFactory.getLogger(PressureServiceImpl.class);
    private static final int DURATION_OF_CALIBRATION = 2000;
    private static final int NUMBER_OF_ITERATIONS_OF_CALIBRATION = 10;

    private BMP180 bmp180 = new BMP180();
    private float normalPressure = 0;

    private ReadWriteLock lock = new ReentrantReadWriteLock();

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
            return bmp180.readPressure();
        } catch (Exception e) {
            LOG.error("Unable to read pressure!", e);
            throw new PressureException("Unable to read pressure!", e);
        } finally {
            lock.readLock().unlock();
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
                float pressure = bmp180.readPressure();
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
