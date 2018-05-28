package com.github.pyro2266.lightit.led.core;

import com.github.pyro2266.lightit.led.modes.impl.base.SimpleColorMode;
import com.github.pyro2266.lightit.led.modes.impl.base.SimpleColorModeConfig;
import com.github.pyro2266.lightit.led.modes.impl.base.SimpleRainbowMode;
import com.github.pyro2266.lightit.led.modes.impl.overlay.BrightnessByPressureMode;
import com.github.pyro2266.lightit.led.modes.impl.overlay.BrightnessByPressureModeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModeSwitcher {

    private static final Logger LOG = LoggerFactory.getLogger(ModeSwitcher.class);

    @Autowired
    private LedService ledService;

    @Autowired
    SimpleColorMode simpleColorMode;

    @Autowired
    SimpleRainbowMode simpleRainbowMode;

    @Autowired
    BrightnessByPressureMode brightnessByPressureMode;

    public void activateSimpleColorMode(SimpleColorModeConfig simpleColorModeConfig) {
        LOG.info("Activating {} with config {}", SimpleColorMode.class, simpleColorModeConfig);
        simpleColorMode.setConfig(simpleColorModeConfig);
        ledService.setBaseLedMode(simpleColorMode);
    }

    public void activateSimpleRainbowMode() {
        LOG.info("Activating {} with config {}", SimpleRainbowMode.class);
        ledService.setBaseLedMode(simpleRainbowMode);
    }

    public void activateBrightnessByPressureMode(BrightnessByPressureModeConfig brightnessByPressureModeConfig) {
        LOG.info("Activating {} with config {}", BrightnessByPressureMode.class, brightnessByPressureModeConfig);
        brightnessByPressureMode.setConfig(brightnessByPressureModeConfig);
        ledService.setOverlayLedMode(brightnessByPressureMode);
    }


}
