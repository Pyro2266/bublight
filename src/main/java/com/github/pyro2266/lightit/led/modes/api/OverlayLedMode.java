package com.github.pyro2266.lightit.led.modes.api;

import com.github.pyro2266.lightit.led.core.Color;

public interface OverlayLedMode extends LedMode {

    /**
     * Modify base color configuration.
     */
    Color[] getNextColors(Color[] baseColors) throws LedModeException;

}
