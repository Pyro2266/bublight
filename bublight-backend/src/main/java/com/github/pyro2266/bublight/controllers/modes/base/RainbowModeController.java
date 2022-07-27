package com.github.pyro2266.bublight.controllers.modes.base;

import com.github.pyro2266.bublight.service.colormodes.ColorModesService;
import com.github.pyro2266.bublight.service.colormodes.data.base.simplecolor.SimpleColorMode;
import com.github.pyro2266.bublight.service.colormodes.data.base.simplerainbow.SimpleRainbowMode;
import com.github.pyro2266.bublight.service.colormodes.data.base.simplerainbow.SimpleRainbowModeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/mode/base/simpleRainbowMode")
public class RainbowModeController {

    private static final Logger LOG = LoggerFactory.getLogger(RainbowModeController.class);

    private final ColorModesService colorModesService;
    private final SimpleRainbowMode simpleRainbowMode;

    @Autowired
    public RainbowModeController(ColorModesService colorModesService, SimpleRainbowMode simpleRainbowMode) {
        this.colorModesService = colorModesService;
        this.simpleRainbowMode = simpleRainbowMode;
    }

    @PostMapping
    public ResponseEntity activateSimpleRainbowMode(@RequestBody SimpleRainbowModeConfig config) {
        LOG.info("Activating {}", SimpleRainbowMode.class);
        simpleRainbowMode.setConfig(config);
        colorModesService.setBaseLedMode(simpleRainbowMode);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity getCurrentSimpleRainbowModeConfig() {
        SimpleRainbowModeConfig config = simpleRainbowMode.getConfig();
        LOG.info("Current config for {} is {}", SimpleRainbowMode.class, config);
        return ResponseEntity.ok(config);
    }

    @PostMapping(path = "/default")
    public ResponseEntity setDefault(){
        LOG.info("Setting default config for {}", SimpleRainbowMode.class);
        simpleRainbowMode.setDefault();
        colorModesService.setBaseLedMode(simpleRainbowMode);
        return ResponseEntity.ok().build();
    }

}
