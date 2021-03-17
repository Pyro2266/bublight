package com.github.pyro2266.bublight.led.core;

import com.github.pyro2266.bublight.modes.Color;
import org.springframework.stereotype.Service;

@Service
public interface LedRendererService {

    /**
     * Render colors on LED strip.
     */
    void renderColors(Color[] colors);

}
