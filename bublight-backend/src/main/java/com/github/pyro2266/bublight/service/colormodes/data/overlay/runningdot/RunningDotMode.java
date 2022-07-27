package com.github.pyro2266.bublight.service.colormodes.data.overlay.runningdot;

import com.github.pyro2266.bublight.configuration.BubLightConfiguration;
import com.github.pyro2266.bublight.service.colormodes.data.Color;
import com.github.pyro2266.bublight.service.colormodes.data.OverlayLedMode;
import com.github.pyro2266.bublight.service.pressure.data.PressureException;
import com.github.pyro2266.bublight.service.pressure.PressureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RunningDotMode implements OverlayLedMode {

    private static final Logger LOG = LoggerFactory.getLogger(RunningDotMode.class);
    private static final String MODE_ID = "Running dot";

    private final PressureService pressureService;
    private final int maxPosition;

    private RunningDotModeConfig config = new RunningDotModeConfig();

    private float currentPosition = 0;

    @Autowired
    public RunningDotMode(BubLightConfiguration bubLightConfiguration, PressureService pressureService) {
        this.pressureService = pressureService;
        this.maxPosition = bubLightConfiguration.getLedCount();
    }

    @Override
    public String getModeId() {
        return MODE_ID;
    }

    @Override
    public void setDefault() {
        config = new RunningDotModeConfig();
    }

    @Override
    public Color[] getNextColors(Color[] baseColors) {
        if (config != null) {
            try {
                float pressureDiff = pressureService.getPressureDifference();

                float moveBy;
                if (pressureDiff > -config.getPressureDiffThreshold() && pressureDiff < config
                        .getPressureDiffThreshold()) {
                    LOG.trace("Pressure diff {} is within threshold {}", pressureDiff, config.getPressureDiffThreshold());
                    moveBy = config.getDefaultSpeed();
                } else  if (pressureDiff > 0) {
                    moveBy = pressureDiff / config.getPositivePressureStep() * config.getMovePerStep();
                } else {
                    moveBy = pressureDiff / config.getNegativePressureStep() * config.getMovePerStep();
                }

                if (config.isClockwiseRotation()) {
                    moveBy = -1 * moveBy;
                }

                currentPosition = currentPosition + moveBy;

                if (currentPosition > maxPosition) {
                    currentPosition -= maxPosition;
                } else if (currentPosition < 0) {
                    currentPosition = maxPosition - 1;
                }

                LOG.trace("PressureDiff: {}; moveBy: {}; currentPosition: {}; runnerPosition: {}", pressureDiff, moveBy,
                        currentPosition, (int) currentPosition);
                baseColors[(int) currentPosition] = config.getRunnerColor();

            } catch (PressureException e) {
                LOG.error("Unable to get pressure", e);
            }

        } else {
            LOG.error("Mode configuration is not set!");
        }
        return baseColors;
    }


    public RunningDotModeConfig getConfig() {
        return config;
    }

    public void setConfig(RunningDotModeConfig config) {
        this.config = config;
    }
}
