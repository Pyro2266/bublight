package com.github.pyro2266.bublight.modes.base;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.pyro2266.bublight.modes.Color;
import java.util.Arrays;

public class SimpleColorModeConfig {

    private Color[] colors;

    @JsonCreator
    public SimpleColorModeConfig(@JsonProperty("colors") Color[] colors) {
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
