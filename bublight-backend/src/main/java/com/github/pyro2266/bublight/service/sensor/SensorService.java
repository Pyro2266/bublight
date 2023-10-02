package com.github.pyro2266.bublight.service.sensor;

import com.github.pyro2266.bublight.service.sensor.data.SensorException;

public interface SensorService {

    /**
     * Get current pressure from pressure sensor.
     */
    float getPressure() throws SensorException;

    /**
     * Get difference between current and normal pressure - pressure in calm state.
     */
    float getPressureDifference() throws SensorException;

    /**
     * Calibrate normal pressure - pressure in calm state.
     */
    void calibrateNormalPressure() throws SensorException;

    /**
     * Get current temperature from pressure sensor.
     */
    float getTemperature() throws SensorException;
}
