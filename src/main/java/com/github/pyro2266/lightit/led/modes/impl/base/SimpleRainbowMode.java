package com.github.pyro2266.lightit.led.modes.impl.base;

import com.github.pyro2266.lightit.led.core.Color;
import com.github.pyro2266.lightit.led.core.LedService;
import com.github.pyro2266.lightit.led.modes.api.BaseLedMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SimpleRainbowMode implements BaseLedMode {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleRainbowMode.class);
    private static final String MODE_ID = "Simple rainbow";
    private static final int MAX_HUE = 1;

    private float hue = 0;
    private float saturation = 1;
    private float value = 1;

    private SimpleRainbowModeConfig config = new SimpleRainbowModeConfig();

    @Override
    public String getModeId() {
        return MODE_ID;
    }

    @Override
    public Color[] getNextColors() {
        Color[] newColors = new Color[LedService.LED_COUNT];
        for (int i = 0; i < newColors.length; i++) {
            newColors[i] = new Color(hue, saturation, value);
        }
        hue = hue % MAX_HUE + config.getStep();
        return newColors;
    }

    public float getHue() {
        return hue;
    }

    public void setHue(float hue) {
        this.hue = hue;
    }

    public float getSaturation() {
        return saturation;
    }

    public void setSaturation(float saturation) {
        this.saturation = saturation;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public SimpleRainbowModeConfig getConfig() {
        return config;
    }

    public void setConfig(SimpleRainbowModeConfig config) {
        this.config = config;
    }
}
