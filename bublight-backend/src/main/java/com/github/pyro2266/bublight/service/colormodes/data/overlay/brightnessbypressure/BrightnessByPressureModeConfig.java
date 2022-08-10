package com.github.pyro2266.bublight.service.colormodes.data.overlay.brightnessbypressure;

public class BrightnessByPressureModeConfig {

    /**
     * Is used to determine how bright should the light be. If pressure difference reaches this value (or higher) then
     * light should be at max brightness.
     */
    private float positivePressureRange = 800;

    /**
     * Same as positive pressure range but for negative pressure difference.
     */
    private float negativePressureRange = 200;

    /**
     * How bright should light be in idle state
     */
    private float defaultBrightness = 0.2f;

    /**
     * Brightness will not be set lower than this value.
     */
    private float minBrightness = 0.05f;

    /**
     * Brightness will not be set higher than this value.
     */
    private float maxBrightness = 1f;

    /**
     * By how much can be brightness changed in one iteration.
     */
    private float maxStep = 0.05f;

    /**
     * If difference between last processed pressure difference and current difference is smaller than this threshold
     * then last processed difference pressure is used.
     */
    private float pressureDiffThreshold = 15;

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

    public float getPressureDiffThreshold() {
        return pressureDiffThreshold;
    }

    public void setPressureDiffThreshold(final float pressureDiffThreshold) {
        this.pressureDiffThreshold = pressureDiffThreshold;
    }

    @Override
    public String toString() {
        return "BrightnessByPressureModeConfig{" + "positivePressureRange=" + positivePressureRange
                + ", negativePressureRange=" + negativePressureRange + ", defaultBrightness=" + defaultBrightness
                + ", minBrightness=" + minBrightness + ", maxBrightness=" + maxBrightness + ", maxStep=" + maxStep
                + '}';
    }
}
