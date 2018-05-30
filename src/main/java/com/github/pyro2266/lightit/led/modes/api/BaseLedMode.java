package com.github.pyro2266.lightit.led.modes.api;

import com.github.pyro2266.lightit.led.core.Color;

// Marker for base led mode
public interface BaseLedMode extends LedMode {

    Color[] getNextColors() throws LedModeException;

}
