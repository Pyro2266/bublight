package com.github.pyro2266.bublight.controllers.modes.overlay;

import com.github.pyro2266.bublight.service.colormodes.ColorModesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/mode/overlay")
public class BaseOverlayModeController {

    private static final Logger LOG = LoggerFactory.getLogger(BaseOverlayModeController.class);

    private final ColorModesService colorModesService;

    @Autowired
    public BaseOverlayModeController(ColorModesService colorModesService) {
        this.colorModesService = colorModesService;
    }

    @PostMapping(path = "/disable")
    public ResponseEntity activateRunningDotMode() {
        LOG.info("Disabling overlay mode");
        colorModesService.disableOverlayMode();
        return ResponseEntity.ok().build();
    }
}
