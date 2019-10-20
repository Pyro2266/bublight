package com.github.pyro2266.lightit.modes;

public interface OverlayLedMode extends LedMode {

    /**
     * Modify base color configuration.
     */
    Color[] getNextColors(Color[] baseColors) throws LedModeException;

}
