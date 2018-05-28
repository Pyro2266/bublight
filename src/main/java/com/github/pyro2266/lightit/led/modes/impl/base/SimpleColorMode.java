package com.github.pyro2266.lightit.led.modes.impl.base;

import com.github.pyro2266.lightit.led.modes.api.BaseLedMode;
import com.github.pyro2266.lightit.led.modes.api.LedModeException;
import java.awt.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SimpleColorMode implements BaseLedMode {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleColorMode.class);
    private static final String MODE_ID = "Simple";

    private SimpleColorModeConfig config;

    @Override
    public String getModeId() {
        return MODE_ID;
    }

    @Override
    public Color[] getNextColors() throws LedModeException {
        if (config != null && config.getColors() != null) {
            return config.getColors();
        } else {
            LOG.warn("Colors are not set!");
            throw new LedModeException("Colors are not set!");
        }
    }

    public SimpleColorModeConfig getConfig() {
        return config;
    }

    public void setConfig(SimpleColorModeConfig config) {
        this.config = config;
    }
}
