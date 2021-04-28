package com.github.pyro2266.bublight.service.ledcore.impl;

import com.github.pyro2266.bublight.service.ledcore.LedRendererService;
import com.github.pyro2266.bublight.service.colormodes.data.Color;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LedRendererServiceSimulatedImpl implements LedRendererService {

    private static final Logger LOG = LoggerFactory.getLogger(LedRendererServiceSimulatedImpl.class);

    public LedRendererServiceSimulatedImpl() {
        LOG.info("Starting SIMULATED LED renderer service...");
    }

    @Override
    public void renderColors(Color[] colors) {
        LOG.trace("Rendering {}", Arrays.asList(colors).toString());
    }
}
