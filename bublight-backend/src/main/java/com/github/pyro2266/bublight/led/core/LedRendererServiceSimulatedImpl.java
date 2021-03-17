package com.github.pyro2266.bublight.led.core;

import com.github.pyro2266.bublight.modes.Color;
import com.github.pyro2266.bublight.modes.ColorModesProcessorImpl;
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
