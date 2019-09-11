package com.github.pyro2266.lightit.led.core;

import com.github.pyro2266.lightit.modes.Color;

public interface LedRendererService {

    /**
     * Render colors on LED strip.
     */
    void renderColors(Color[] colors);

}
