package com.github.pyro2266.bublight.controllers.modes.overlay;

import com.github.pyro2266.bublight.service.colormodes.ColorModesService;
import com.github.pyro2266.bublight.service.colormodes.data.base.simplecolor.SimpleColorMode;
import com.github.pyro2266.bublight.service.colormodes.data.overlay.brightnessbypressure.BrightnessByPressureMode;
import com.github.pyro2266.bublight.service.colormodes.data.overlay.brightnessbypressure.BrightnessByPressureModeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/mode/overlay/brightnessByPressureMode")
public class BrightnessByPressureOverlayController {

    private static final Logger LOG = LoggerFactory.getLogger(BrightnessByPressureOverlayController.class);

    private final ColorModesService colorModesService;
    private final BrightnessByPressureMode brightnessByPressureMode;

    @Autowired
    public BrightnessByPressureOverlayController(ColorModesService colorModesService, BrightnessByPressureMode brightnessByPressureMode) {
        this.colorModesService = colorModesService;
        this.brightnessByPressureMode = brightnessByPressureMode;
    }

    @PostMapping
    public ResponseEntity activateBrightnessByPressureMode(@RequestBody BrightnessByPressureModeConfig config) {
        LOG.info("Activating {} with config {}", BrightnessByPressureMode.class, config);
        brightnessByPressureMode.setConfig(config);
        colorModesService.setOverlayLedMode(brightnessByPressureMode);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity getCurrentBrightnessByPressureModeConfig() {
        BrightnessByPressureModeConfig config = brightnessByPressureMode.getConfig();
        LOG.info("Current config for {} is {}", BrightnessByPressureMode.class, config);
        return ResponseEntity.ok(config);
    }

    @PostMapping(path = "/default")
    public ResponseEntity setDefault(){
        LOG.info("Setting default config for {}", BrightnessByPressureMode.class);
        brightnessByPressureMode.setDefault();
        colorModesService.setOverlayLedMode(brightnessByPressureMode);
        return ResponseEntity.ok().build();
    }

}
