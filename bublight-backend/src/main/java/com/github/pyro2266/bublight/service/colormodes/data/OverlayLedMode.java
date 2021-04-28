package com.github.pyro2266.bublight.service.colormodes.data;

public interface OverlayLedMode extends LedMode {

    /**
     * Modify base color configuration.
     */
    Color[] getNextColors(Color[] baseColors) throws LedModeException;

}
