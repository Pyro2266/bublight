package com.github.pyro2266.bublight.modes.base.simplerainbow;

public class SimpleRainbowModeConfig {

    private float step = 0.0001f;

    public float getStep() {
        return step;
    }

    public void setStep(float step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "SimpleRainbowModeConfig{" + "step=" + step + '}';
    }
}
