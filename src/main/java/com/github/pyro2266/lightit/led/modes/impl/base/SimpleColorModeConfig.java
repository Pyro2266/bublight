package com.github.pyro2266.lightit.led.modes.impl.base;

import com.github.pyro2266.lightit.led.core.Color;
import com.github.pyro2266.lightit.led.core.LedServiceImpl;
import java.util.Arrays;

public class SimpleColorModeConfig {

    private Color[] colors;

    public SimpleColorModeConfig(Color[] colors) {
        this.colors = colors;
    }

    public SimpleColorModeConfig() {
        colors = new Color[LedServiceImpl.LED_COUNT];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = new Color();
        }
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
