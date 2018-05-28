package com.github.pyro2266.lightit.led.modes.api;

import java.awt.Color;

// Marker for base led mode
public interface BaseLedMode extends LedMode {

    Color[] getNextColors() throws LedModeException;

}
