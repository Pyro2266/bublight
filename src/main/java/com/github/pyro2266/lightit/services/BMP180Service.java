package com.github.pyro2266.lightit.services;

import itx.rpi.hardware.gpio.sensors.BMP180;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BMP180Service {

    private static final Logger LOG = LoggerFactory.getLogger(BMP180Service.class);

    private BMP180 bmp180;
    private float normalPressure;

    public BMP180Service() {
        this.bmp180 = new BMP180();
        this.normalPressure = 0;
    }

    public float readPressure() throws BMP180Exception {
        try {
            return bmp180.readPressure();
        } catch (Exception e) {
            LOG.error("Unable to read pressure!", e);
            throw new BMP180Exception("Unable to read pressure!", e);
        }
    }

    public float getPressureDifference() throws BMP180Exception {
        return normalPressure - readPressure();
    }

    public void calibrateNormalPressure() throws BMP180Exception {
        float sum = 0;
        int numOfIterations = 10;
        for (int i = 0; i < numOfIterations; i++) {
            sum += readPressure();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                LOG.error("Unable to calibrate normal pressure!", e);
                throw new BMP180Exception("Unable to calibrate normal pressure!", e);
            }
        }
        normalPressure = sum/numOfIterations;
        LOG.info("Normal pressure calibrated to {}", normalPressure);
    }
}
