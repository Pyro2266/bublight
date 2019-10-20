package com.github.pyro2266.lightit.controllers;

import com.github.pyro2266.lightit.modes.ColorModesProcessor;
import com.github.pyro2266.lightit.modes.base.SimpleColorMode;
import com.github.pyro2266.lightit.modes.base.SimpleColorModeConfig;
import com.github.pyro2266.lightit.modes.base.SimpleRainbowMode;
import com.github.pyro2266.lightit.modes.base.SimpleRainbowModeConfig;
import com.github.pyro2266.lightit.modes.overlay.BrightnessByPressureMode;
import com.github.pyro2266.lightit.modes.overlay.BrightnessByPressureModeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/mode")
public class LedModeController {

    private static final Logger LOG = LoggerFactory.getLogger(LedModeController.class);

    private ColorModesProcessor colorModesProcessor;
    private SimpleColorMode simpleColorMode;
    private SimpleRainbowMode simpleRainbowMode;
    private BrightnessByPressureMode brightnessByPressureMode;

    @Autowired
    public LedModeController(ColorModesProcessor colorModesProcessor, SimpleColorMode simpleColorMode,
            SimpleRainbowMode simpleRainbowMode, BrightnessByPressureMode brightnessByPressureMode) {
        this.colorModesProcessor = colorModesProcessor;
        this.simpleColorMode = simpleColorMode;
        this.simpleRainbowMode = simpleRainbowMode;
        this.brightnessByPressureMode = brightnessByPressureMode;
    }

    @PostMapping(path = "/base/simpleColorMode")
    public ResponseEntity activateSimpleColorMode(@RequestBody SimpleColorModeConfig config) {
        LOG.info("Activating {} with config {}", SimpleColorMode.class, config);
        simpleColorMode.setConfig(config);
        colorModesProcessor.setBaseLedMode(simpleColorMode);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/base/simpleColorMode")
    public ResponseEntity getCurrentSimpleColorModeConfig() {
        SimpleColorModeConfig config = simpleColorMode.getConfig();
        LOG.info("Current config for {} is {}", SimpleColorMode.class, config);
        return ResponseEntity.ok(config);
    }

    @PostMapping(path = "/base/simpleRainbowMode")
    public ResponseEntity activateSimpleRainbowMode(@RequestBody SimpleRainbowModeConfig config) {
        LOG.info("Activating {}", SimpleRainbowMode.class);
        colorModesProcessor.setBaseLedMode(simpleRainbowMode);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/base/simpleRainbowMode")
    public ResponseEntity getCurrentSimpleRainbowModeConfig() {
        SimpleRainbowModeConfig config = simpleRainbowMode.getConfig();
        LOG.info("Current config for {} is {}", SimpleRainbowMode.class, config);
        return ResponseEntity.ok(config);
    }

    @PostMapping(path = "/overlay/brightnessByPressureMode")
    public ResponseEntity activateBrightnessByPressureMode(@RequestBody BrightnessByPressureModeConfig config) {
        LOG.info("Activating {} with config {}", BrightnessByPressureMode.class, config);
        brightnessByPressureMode.setConfig(config);
        colorModesProcessor.setOverlayLedMode(brightnessByPressureMode);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/overlay/brightnessByPressureMode")
    public ResponseEntity getCurrentBrightnessByPressureModeConfig() {
        BrightnessByPressureModeConfig config = brightnessByPressureMode.getConfig();
        LOG.info("Current config for {} is {}", BrightnessByPressureMode.class, config);
        return ResponseEntity.ok(config);
    }
}
