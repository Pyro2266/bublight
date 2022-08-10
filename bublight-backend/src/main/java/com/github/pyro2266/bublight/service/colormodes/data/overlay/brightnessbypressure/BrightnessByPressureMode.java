package com.github.pyro2266.bublight.service.colormodes.data.overlay.brightnessbypressure;

import com.github.pyro2266.bublight.service.colormodes.data.Color;
import com.github.pyro2266.bublight.service.colormodes.data.OverlayLedMode;
import com.github.pyro2266.bublight.service.pressure.PressureService;
import com.github.pyro2266.bublight.service.pressure.data.PressureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BrightnessByPressureMode implements OverlayLedMode {

    private static final Logger LOG = LoggerFactory.getLogger(BrightnessByPressureMode.class);
    private static final String MODE_ID = "Brightness by pressure";

    private final PressureService pressureService;

    private final float[] hsb = new float[3];
    private BrightnessByPressureModeConfig config = new BrightnessByPressureModeConfig();

    private float actualBrightness = config.getDefaultBrightness();
    private float lastProcessedPressureDifference = 0;


    @Autowired
    public BrightnessByPressureMode(PressureService pressureService) {
        this.pressureService = pressureService;
    }

    @Override
    public String getModeId() {
        return MODE_ID;
    }

    /**
     * Takes pressure difference from sensor (calibrated pressure minus actual pressure) and divides it by positive
     * pressure range (negative if difference is negative). This number is then multiplied
     * by 'max brightness minus default brightness' (or min brightness if negative).
     * This number represents brightness delta to be applied. Roughly this number represents (in percentage
     * if multiplied by 100) by how much should be brightness changed.
     * Then default brightness is added to this value which creates target brightness.
     *
     * Target brightness is how bright should light be. But it's not set to this value right away as max step
     * comes in play.
     *
     * In each iteration actual brightness set is stored. New actual brightness in each iteration is calculated by
     * adding max step to actual brightness. This means that in one iteration brightness will not be changed by value
     * higher than max step value.
     *
     */
    @Override
    public void setDefault() {
        config = new BrightnessByPressureModeConfig();
    }

    @Override
    public Color[] getNextColors(Color[] baseColors) {
        if (config != null) {
            try {
                float pressureDiff = getPressureDiffToUse(pressureService.getPressureDifference());

                float brightnessDelta = calculateBrightnessDelta(pressureDiff);

                float targetBrightness = config.getDefaultBrightness() + brightnessDelta;
                float brightnessDifference = targetBrightness - actualBrightness;

                float step = calculateStep(brightnessDifference);
                updateBrightness(baseColors, step);
            } catch (PressureException e) {
                LOG.error("Unable to set brightness", e);
            }
        } else {
            LOG.error("Mode configuration is not set!");
        }
        return baseColors;
    }

    private float getPressureDiffToUse(final float currentPressureDiff) {
        float pressureDiff;
        if (shouldProcess(currentPressureDiff)) {
            lastProcessedPressureDifference = currentPressureDiff;
            pressureDiff = currentPressureDiff;
        } else {
            LOG.debug("Pressure diff within threshold - using last processed diff. Current diff: {}; "
                    + "last processed diff: {}", currentPressureDiff, lastProcessedPressureDifference);
            pressureDiff = lastProcessedPressureDifference;
        }
        return pressureDiff;
    }

    private boolean shouldProcess(final float pressureDiff) {
        return Math.abs(lastProcessedPressureDifference - pressureDiff) > config.getPressureDiffThreshold();
    }

    private void updateBrightness(final Color[] baseColors, final float step) {
        actualBrightness += step;
        if (actualBrightness > config.getMaxBrightness()) {
            actualBrightness = config.getMaxBrightness();
        }
        if (actualBrightness < config.getMinBrightness()) {
            actualBrightness = config.getMinBrightness();
        }

        for (int i = 0; i < baseColors.length; i++) {
            Color.rgbToHsb(baseColors[i].getRed(), baseColors[i].getGreen(), baseColors[i].getBlue(),
                    hsb);
            baseColors[i] = new Color(hsb[0], hsb[1], actualBrightness);
        }
    }

    private float calculateStep(final float brightnessDifference) {
        float step;
        if (Math.abs(brightnessDifference) > config.getMaxStep()) {
            step = config.getMaxStep() * (brightnessDifference < 0 ? -1 : 1);
        } else {
            step = brightnessDifference;
        }
        return step;
    }

    private float calculateBrightnessDelta(final float pressureDif) {
        float brightnessDelta;
        if (pressureDif >= 0) {
            brightnessDelta = trimDecimal(pressureDif / config.getPositivePressureRange())
                    * (config.getMaxBrightness() - config.getDefaultBrightness());
        } else {
            brightnessDelta = trimDecimal(pressureDif / config.getNegativePressureRange())
                    * (config.getDefaultBrightness() - config.getMinBrightness());
        }
        return brightnessDelta;
    }

    private float trimDecimal(float decimal) {
        if (decimal > 1) {
            return 1;
        } else if (decimal < -1) {
            return -1;
        } else {
            return decimal;
        }
    }

    public BrightnessByPressureModeConfig getConfig() {
        return config;
    }

    public void setConfig(BrightnessByPressureModeConfig config) {
        this.config = config;
    }
}
