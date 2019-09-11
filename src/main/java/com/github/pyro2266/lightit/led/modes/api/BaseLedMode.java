package com.github.pyro2266.lightit.led.modes.api;

import com.github.pyro2266.lightit.led.core.Color;

public interface BaseLedMode extends LedMode {

    /**
     * Get color configuration that will be used as base in next color rendering.
     */
    Color[] getNextColors() throws LedModeException;

}
