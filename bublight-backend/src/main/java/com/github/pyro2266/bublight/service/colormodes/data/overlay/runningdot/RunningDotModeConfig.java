package com.github.pyro2266.bublight.service.colormodes.data.overlay.runningdot;

import com.github.pyro2266.bublight.service.colormodes.data.Color;

public class RunningDotModeConfig {

    private float positivePressureStep = 50;
    private float negativePressureStep = 20;
    private float pressureDiffThreshold = 25;
    private boolean clockwiseRotation = false;
    private float defaultSpeed = 0.1f;
    private float movePerStep = 0.01f;
    private Color runnerColor = new Color(255, 255, 255);

    public float getPositivePressureStep() {
        return positivePressureStep;
    }

    public void setPositivePressureStep(float positivePressureStep) {
        this.positivePressureStep = positivePressureStep;
    }

    public float getNegativePressureStep() {
        return negativePressureStep;
    }

    public void setNegativePressureStep(float negativePressureStep) {
        this.negativePressureStep = negativePressureStep;
    }

    public float getPressureDiffThreshold() {
        return pressureDiffThreshold;
    }

    public void setPressureDiffThreshold(float pressureDiffThreshold) {
        this.pressureDiffThreshold = pressureDiffThreshold;
    }

    public boolean isClockwiseRotation() {
        return clockwiseRotation;
    }

    public void setClockwiseRotation(boolean clockwiseRotation) {
        this.clockwiseRotation = clockwiseRotation;
    }

    public float getDefaultSpeed() {
        return defaultSpeed;
    }

    public void setDefaultSpeed(float defaultSpeed) {
        this.defaultSpeed = defaultSpeed;
    }

    public float getMovePerStep() {
        return movePerStep;
    }

    public void setMovePerStep(float movePerStep) {
        this.movePerStep = movePerStep;
    }

    public Color getRunnerColor() {
        return runnerColor;
    }

    public void setRunnerColor(Color runnerColor) {
        this.runnerColor = runnerColor;
    }

    @Override
    public String toString() {
        return "RunningDotModeConfig{" + "positivePressureStep=" + positivePressureStep + ", negativePressureStep="
                + negativePressureStep + ", defaultSpeed=" + defaultSpeed + ", speedPerStep=" + movePerStep + '}';
    }
}
