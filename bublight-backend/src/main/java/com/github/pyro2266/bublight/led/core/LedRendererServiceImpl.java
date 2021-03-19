package com.github.pyro2266.bublight.led.core;

import com.github.mbelling.ws281x.LedStrip;
import com.github.mbelling.ws281x.LedStripType;
import com.github.mbelling.ws281x.Ws281xLedStrip;
import com.github.pyro2266.bublight.configuration.BubLightConfiguration;
import com.github.pyro2266.bublight.modes.Color;
import org.springframework.beans.factory.annotation.Autowired;

public class LedRendererServiceImpl implements LedRendererService {

    private final LedStrip strip;

    @Autowired
    public LedRendererServiceImpl(BubLightConfiguration bubLightConfiguration) {
        this.strip = new Ws281xLedStrip(bubLightConfiguration.getLedCount(), 18, 800000, 10, 255, 0, false,
                LedStripType.WS2811_STRIP_GRB, true);
    }

    @Override
    public void renderColors(Color[] colors) {
        for (int i = 0; i < colors.length; i++) {
            strip.setPixel(i, colors[i].getRed(), colors[i].getGreen(), colors[i].getBlue());
        }
        strip.render();
    }
}
