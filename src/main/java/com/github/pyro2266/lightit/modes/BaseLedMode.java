package com.github.pyro2266.lightit.modes;

public interface BaseLedMode extends LedMode {

    /**
     * Get color configuration that will be used as base in next color rendering.
     */
    Color[] getNextColors() throws LedModeException;

}
