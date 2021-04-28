package com.github.pyro2266.bublight.service.colormodes.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface BaseLedMode extends LedMode {

    /**
     * Get color configuration that will be used as base in next color rendering.
     */
    @JsonIgnore
    Color[] getNextColors() throws LedModeException;

}
