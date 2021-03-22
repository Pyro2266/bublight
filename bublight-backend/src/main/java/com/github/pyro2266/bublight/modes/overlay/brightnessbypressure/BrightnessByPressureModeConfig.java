package com.github.pyro2266.bublight.modes.overlay.brightnessbypressure;

public class BrightnessByPressureModeConfig {

    private float positivePressureRange = 200;
    private float negativePressureRange = 100;
    private float defaultBrightness = 0.2f;
    private float minBrightness = 0.05f;
    private float maxBrightness = 1f;
    private float maxStep = 0.05f;

    public float getPositivePressureRange() {
        return positivePressureRange;
    }

    public void setPositivePressureRange(float positivePressureRange) {
        this.positivePressureRange = positivePressureRange;
    }

    public float getNegativePressureRange() {
        return negativePressureRange;
    }

    public void setNegativePressureRange(float negativePressureRange) {
        this.negativePressureRange = negativePressureRange;
    }

    public float getDefaultBrightness() {
        return defaultBrightness;
    }

    public void setDefaultBrightness(float defaultBrightness) {
        this.defaultBrightness = defaultBrightness;
    }

    public float getMinBrightness() {
        return minBrightness;
    }

    public void setMinBrightness(float minBrightness) {
        this.minBrightness = minBrightness;
    }

    public float getMaxBrightness() {
        return maxBrightness;
    }

    public void setMaxBrightness(float maxBrightness) {
        this.maxBrightness = maxBrightness;
    }

    public float getMaxStep() {
        return maxStep;
    }

    public void setMaxStep(float maxStep) {
        this.maxStep = maxStep;
    }

    @Override
    public String toString() {
        return "BrightnessByPressureModeConfig{" + "positivePressureRange=" + positivePressureRange
                + ", negativePressureRange=" + negativePressureRange + ", defaultBrightness=" + defaultBrightness
                + ", minBrightness=" + minBrightness + ", maxBrightness=" + maxBrightness + ", maxStep=" + maxStep
                + '}';
    }
}
