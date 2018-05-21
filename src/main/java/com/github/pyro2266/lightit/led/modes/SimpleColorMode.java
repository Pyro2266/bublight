package com.github.pyro2266.lightit.led.modes;

import com.github.mbelling.ws281x.Color;

public class SimpleColorMode implements LedMode {

    private static final String MODE_ID = "Simple";

    @Override
    public String getModeId() {
        return MODE_ID;
    }

    @Override
    public Color[] getNextColors(Color[] previousColors) {
        return new Color[0];
    }
}
