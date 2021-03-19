package com.github.pyro2266.bublight.pressure;

public interface PressureService {

    /**
     * Get current pressure from pressure sensor.
     */
    float getPressure() throws PressureException;

    /**
     * Get difference between current and normal pressure - pressure in calm state.
     */
    float getPressureDifference() throws PressureException;

    /**
     * Get cached pressure from pressure sensor.
     */
    float getCachedPressure();

    /**
     * Get difference between cached and normal pressure - pressure in calm state.
     */
    float getCachedPressureDifference();

    /**
     * Calibrate normal pressure - pressure in calm state.
     */
    void calibrateNormalPressure() throws PressureException;
}
