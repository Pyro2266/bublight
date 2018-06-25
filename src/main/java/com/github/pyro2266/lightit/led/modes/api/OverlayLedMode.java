package com.github.pyro2266.lightit.led.modes.api;

import com.github.pyro2266.lightit.led.core.Color;

// Marker for overlay led mode
public interface OverlayLedMode extends LedMode {

    Color[] getNextColors(Color[] baseColors) throws LedModeException;

}
