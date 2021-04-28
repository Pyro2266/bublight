package com.github.pyro2266.bublight.service.colormodes.data.overlay.brightnessbypressure;

import com.github.pyro2266.bublight.service.colormodes.data.Color;
import com.github.pyro2266.bublight.service.colormodes.data.OverlayLedMode;
import com.github.pyro2266.bublight.service.pressure.data.PressureException;
import com.github.pyro2266.bublight.service.pressure.PressureService;
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


    @Autowired
    public BrightnessByPressureMode(PressureService pressureService) {
        this.pressureService = pressureService;
    }

    @Override
    public String getModeId() {
        return MODE_ID;
    }

    @Override
    public Color[] getNextColors(Color[] baseColors) {
        if (config != null) {
            try {
                // TODO refactor... hah, it's nearly three years now
                float pressureDif = pressureService.getPressureDifference();

                float brightnessDelta;
                if (pressureDif >= 0) {
                    brightnessDelta = trimDecimal(pressureDif / config.getPositivePressureRange())
                            * (config.getMaxBrightness() - config.getDefaultBrightness());
                } else {
                    brightnessDelta = trimDecimal(pressureDif / config.getNegativePressureRange())
                            * (config.getDefaultBrightness() - config.getMinBrightness());
                }

                float targetBrightness = config.getDefaultBrightness() + brightnessDelta;

                float brightnessDifference = targetBrightness - actualBrightness;
                float step;
                if (Math.abs(brightnessDifference) > config.getMaxStep()) {
                    step = config.getMaxStep() * (brightnessDifference < 0 ? -1 : 1);
                } else {
                    step = brightnessDifference;
                }

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
            } catch (PressureException e) {
                LOG.error("Unable to set brightness", e);
            }
        } else {
            LOG.error("Mode configuration is not set!");
        }
        return baseColors;
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
