package com.github.pyro2266.bublight.service.config.impl;

import com.github.pyro2266.bublight.service.config.ConfigService;
import com.github.pyro2266.bublight.service.config.data.CurrentConfig;
import com.github.pyro2266.bublight.service.colormodes.ColorModesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigServiceImpl implements ConfigService {

    private final ColorModesService colorModesService;

    @Autowired
    public ConfigServiceImpl(ColorModesService colorModesService) {
        this.colorModesService = colorModesService;
    }

    @Override
    public CurrentConfig getCurrentConfig() {

        return new CurrentConfig(
                colorModesService.getBaseLedMode(),
                colorModesService.getOverlayMode(),
                colorModesService.isRunning()
        );
    }
}
