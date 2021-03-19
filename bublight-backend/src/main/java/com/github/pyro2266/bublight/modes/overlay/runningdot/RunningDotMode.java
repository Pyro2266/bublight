package com.github.pyro2266.bublight.modes.overlay.runningdot;

import com.github.pyro2266.bublight.configuration.BubLightConfiguration;
import com.github.pyro2266.bublight.modes.Color;
import com.github.pyro2266.bublight.modes.OverlayLedMode;
import com.github.pyro2266.bublight.pressure.PressureException;
import com.github.pyro2266.bublight.pressure.PressureService;
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
    public Color[] getNextColors(Color[] baseColors) {
        if (config != null) {
            try {
                float pressureDif = pressureService.getPressureDifference();

                float moveBy;
                if (pressureDif > 0) {
                    moveBy = pressureDif / config.getPositivePressureStep() * config.getMovePerStep();
                } else {
                    moveBy = pressureDif / config.getNegativePressureStep() * config.getMovePerStep();
                }

                currentPosition = currentPosition + moveBy;

                if (currentPosition > maxPosition) {
                    currentPosition -= maxPosition;
                }

                baseColors[(int)currentPosition % maxPosition] = config.getRunnerColor();
                LOG.trace("PressureDiff: {}; moveBy: {}; currentPosition: {}; runnerPosition: {}", pressureDif, moveBy,
                        currentPosition, (int)currentPosition % maxPosition);

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
