package com.github.pyro2266.lightit.led.modes;

import com.github.mbelling.ws281x.Color;
import com.github.pyro2266.lightit.led.LedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleRainbowMode implements LedMode {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleRainbowMode.class);
    private static final String MODE_ID = "Rainbow";
    private static final int MAX_HUE = 360;

    private int step = 5;
    private int hue = 0;
    private int saturation = 100;
    private int value = 100;

    @Override
    public String getModeId() {
        return MODE_ID;
    }

    @Override
    public Color[] getNextColors(Color[] previousColors) {
        Color[] newColors = new Color[LedService.LED_COUNT];
        for (int i = 0; i < newColors.length; i++) {
            newColors[i] = new Color(java.awt.Color.getHSBColor(hue, saturation, value).getRGB());
        }
        hue = hue % MAX_HUE + step;
        return newColors;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getHue() {
        return hue;
    }

    public void setHue(int hue) {
        this.hue = hue;
    }

    public int getSaturation() {
        return saturation;
    }

    public void setSaturation(int saturation) {
        this.saturation = saturation;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
