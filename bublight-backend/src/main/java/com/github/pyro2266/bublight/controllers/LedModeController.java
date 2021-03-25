package com.github.pyro2266.bublight.controllers;

import com.github.pyro2266.bublight.modes.ColorModesProcessor;
import com.github.pyro2266.bublight.modes.base.simplecolor.SimpleColorMode;
import com.github.pyro2266.bublight.modes.base.simplecolor.SimpleColorModeConfig;
import com.github.pyro2266.bublight.modes.base.simplerainbow.SimpleRainbowMode;
import com.github.pyro2266.bublight.modes.base.simplerainbow.SimpleRainbowModeConfig;
import com.github.pyro2266.bublight.modes.overlay.brightnessbypressure.BrightnessByPressureMode;
import com.github.pyro2266.bublight.modes.overlay.brightnessbypressure.BrightnessByPressureModeConfig;
import com.github.pyro2266.bublight.modes.overlay.runningdot.RunningDotMode;
import com.github.pyro2266.bublight.modes.overlay.runningdot.RunningDotModeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/mode")
public class LedModeController {

    private static final Logger LOG = LoggerFactory.getLogger(LedModeController.class);

    private final ColorModesProcessor colorModesProcessor;
    private final SimpleColorMode simpleColorMode;
    private final SimpleRainbowMode simpleRainbowMode;
    private final BrightnessByPressureMode brightnessByPressureMode;
    private final RunningDotMode runningDotMode;

    @Autowired
    public LedModeController(ColorModesProcessor colorModesProcessor, SimpleColorMode simpleColorMode,
            SimpleRainbowMode simpleRainbowMode, BrightnessByPressureMode brightnessByPressureMode,
            RunningDotMode runningDotMode) {
        this.colorModesProcessor = colorModesProcessor;
        this.simpleColorMode = simpleColorMode;
        this.simpleRainbowMode = simpleRainbowMode;
        this.brightnessByPressureMode = brightnessByPressureMode;
        this.runningDotMode = runningDotMode;
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
        simpleRainbowMode.setConfig(config);
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

    @PostMapping(path = "/overlay/runningDotMode")
    public ResponseEntity activateRunningDotMode(@RequestBody RunningDotModeConfig config) {
        LOG.info("Activating {} with config {}", RunningDotMode.class, config);
        runningDotMode.setConfig(config);
        colorModesProcessor.setOverlayLedMode(runningDotMode);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/overlay/runningDotMode")
    public ResponseEntity getCurrentRunningDotModeConfig() {
        RunningDotModeConfig config = runningDotMode.getConfig();
        LOG.info("Current config for {} is {}", RunningDotMode.class, config);
        return ResponseEntity.ok(config);
    }

    @PostMapping(path = "/overlay/disable")
    public ResponseEntity activateRunningDotMode() {
        LOG.info("Disabling overlay mode");
        colorModesProcessor.disableOverlayMode();
        return ResponseEntity.ok().build();
    }
}
