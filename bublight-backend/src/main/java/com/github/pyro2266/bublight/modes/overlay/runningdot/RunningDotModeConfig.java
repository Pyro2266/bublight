package com.github.pyro2266.bublight.modes.overlay.runningdot;

import com.github.pyro2266.bublight.modes.Color;

public class RunningDotModeConfig {

    private float positivePressureStep = 20;
    private float negativePressureStep = 5;
    private float defaultSpeed = 0.01f;
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
