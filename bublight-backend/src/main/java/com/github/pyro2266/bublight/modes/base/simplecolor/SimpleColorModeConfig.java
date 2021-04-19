package com.github.pyro2266.bublight.modes.base.simplecolor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.pyro2266.bublight.modes.Color;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;

@ToString
public class SimpleColorModeConfig {

    @Getter @Setter
    private Color[] colors;

    @JsonCreator
    public SimpleColorModeConfig(@JsonProperty("colors") Color[] colors) {
        this.colors = colors;
    }
}
