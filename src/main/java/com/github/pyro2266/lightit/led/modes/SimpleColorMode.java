package com.github.pyro2266.lightit.led.modes;

import com.github.mbelling.ws281x.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleColorMode implements LedMode {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleColorMode.class);
    private static final String MODE_ID = "Simple";

    private Color[] colors;

    @Override
    public String getModeId() {
        return MODE_ID;
    }

    @Override
    public Color[] getNextColors(Color[] previousColors) {
        if (colors != null) {
            return colors;
        } else {
            LOG.warn("Colors are not set!");
            return previousColors;
        }
    }

    public Color[] getColors() {
        return colors;
    }

    public void setColors(Color[] colors) {
        this.colors = colors;
    }
}
