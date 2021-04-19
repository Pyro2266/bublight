package com.github.pyro2266.bublight.modes.base.simplerainbow;

import com.github.pyro2266.bublight.configuration.BubLightConfiguration;
import com.github.pyro2266.bublight.modes.BaseLedMode;
import com.github.pyro2266.bublight.modes.Color;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SimpleRainbowMode implements BaseLedMode {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleRainbowMode.class);
    private static final String MODE_ID = "Simple rainbow";
    private static final int MAX_HUE = 1;

    private final BubLightConfiguration bubLightConfiguration;

    @Getter @Setter
    private float hue = 0;
    @Getter @Setter
    private float saturation = 1;
    @Getter @Setter
    private float value = 1;

    @Getter @Setter
    private SimpleRainbowModeConfig config = new SimpleRainbowModeConfig();

    @Autowired
    public SimpleRainbowMode(BubLightConfiguration bubLightConfiguration) {
        this.bubLightConfiguration = bubLightConfiguration;
    }

    @Override
    public String getModeId() {
        return MODE_ID;
    }

    @Override
    public Color[] getNextColors() {
        Color[] newColors = new Color[bubLightConfiguration.getLedCount()];
        for (int i = 0; i < newColors.length; i++) {
            newColors[i] = new Color(hue, saturation, value);
        }
        hue = hue % MAX_HUE + config.getStep();
        return newColors;
    }
}
