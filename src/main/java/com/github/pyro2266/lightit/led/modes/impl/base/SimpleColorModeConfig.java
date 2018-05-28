package com.github.pyro2266.lightit.led.modes.impl.base;

import java.awt.Color;
import java.util.Arrays;

public class SimpleColorModeConfig {

    private Color[] colors;

    public Color[] getColors() {
        return colors;
    }

    public void setColors(Color[] colors) {
        this.colors = colors;
    }

    @Override
    public String toString() {
        return "SimpleColorModeConfig{" + "colors=" + Arrays.toString(colors) + '}';
    }
}
