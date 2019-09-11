package com.github.pyro2266.lightit.modes.base;

import com.github.pyro2266.lightit.led.core.LedRendererServiceImpl;
import com.github.pyro2266.lightit.modes.Color;
import java.util.Arrays;

public class SimpleColorModeConfig {

    private Color[] colors;

    public SimpleColorModeConfig(Color[] colors) {
        this.colors = colors;
    }

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
