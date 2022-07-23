package com.github.pyro2266.bublight.controllers.modes.base;

import com.github.pyro2266.bublight.service.colormodes.ColorModesService;
import com.github.pyro2266.bublight.service.colormodes.data.base.simplecolor.SimpleColorMode;
import com.github.pyro2266.bublight.service.colormodes.data.base.simplecolor.SimpleColorModeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/mode/base/simpleColorMode")
public class ColorModeController {

    private static final Logger LOG = LoggerFactory.getLogger(ColorModeController.class);

    private final ColorModesService colorModesService;
    private final SimpleColorMode simpleColorMode;

    @Autowired
    public ColorModeController(ColorModesService colorModesService, SimpleColorMode simpleColorMode){
        this.colorModesService = colorModesService;
        this.simpleColorMode = simpleColorMode;
    }

    @PostMapping
    public ResponseEntity activateSimpleColorMode(@RequestBody SimpleColorModeConfig config) {
        LOG.info("Activating {} with config {}", SimpleColorMode.class, config);
        simpleColorMode.setConfig(config);
        colorModesService.setBaseLedMode(simpleColorMode);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity getCurrentSimpleColorModeConfig() {
        SimpleColorModeConfig config = simpleColorMode.getConfig();
        LOG.info("Current config for {} is {}", SimpleColorMode.class, config);
        return ResponseEntity.ok(config);
    }

    @PostMapping(path = "/default")
    public ResponseEntity setDefault(){
        LOG.info("Setting default config for {}", SimpleColorMode.class);
        simpleColorMode.setDefault();
        colorModesService.setBaseLedMode(simpleColorMode);
        return ResponseEntity.ok().build();
    }

}
