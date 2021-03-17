package com.github.pyro2266.bublight.modes;

public interface ColorModesProcessor {

    /**
     * Start color rendering loop.
     */
    void startColorLoop();

    /**
     * Stop color rendering loop.
     */
    void stopColorLoop();

    /**
     * Set rate in which are colors rendered.
     */
    void setRefreshRate(int refreshRate);

    /**
     * Set base mode.
     */
    void setBaseLedMode(BaseLedMode baseLedMode);

    /**
     * Set overlay mode.
     */
    void setOverlayLedMode(OverlayLedMode overlayLedMode);
}
