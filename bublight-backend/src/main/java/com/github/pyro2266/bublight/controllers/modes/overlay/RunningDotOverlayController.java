package com.github.pyro2266.bublight.controllers.modes.overlay;

import com.github.pyro2266.bublight.service.colormodes.ColorModesService;
import com.github.pyro2266.bublight.service.colormodes.data.base.simplecolor.SimpleColorMode;
import com.github.pyro2266.bublight.service.colormodes.data.overlay.runningdot.RunningDotMode;
import com.github.pyro2266.bublight.service.colormodes.data.overlay.runningdot.RunningDotModeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/mode/overlay/runningDotMode")
public class RunningDotOverlayController {

    private static final Logger LOG = LoggerFactory.getLogger(RunningDotOverlayController.class);

    private final ColorModesService colorModesService;
    private final RunningDotMode runningDotMode;

    @Autowired
    public RunningDotOverlayController(ColorModesService colorModesService, RunningDotMode runningDotMode) {
        this.colorModesService = colorModesService;
        this.runningDotMode = runningDotMode;
    }

    @PostMapping
    public ResponseEntity activateRunningDotMode(@RequestBody RunningDotModeConfig config) {
        LOG.info("Activating {} with config {}", RunningDotMode.class, config);
        runningDotMode.setConfig(config);
        colorModesService.setOverlayLedMode(runningDotMode);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity getCurrentRunningDotModeConfig() {
        RunningDotModeConfig config = runningDotMode.getConfig();
        LOG.info("Current config for {} is {}", RunningDotMode.class, config);
        return ResponseEntity.ok(config);
    }

    @PostMapping(path = "/default")
    public ResponseEntity setDefault(){
        LOG.info("Setting default config for {}", RunningDotMode.class);
        runningDotMode.setDefault();
        colorModesService.setOverlayLedMode(runningDotMode);
        return ResponseEntity.ok().build();
    }

}
