package com.github.pyro2266.bublight.modes.base.simplecolor;

import com.github.pyro2266.bublight.configuration.BubLightConfiguration;
import com.github.pyro2266.bublight.modes.BaseLedMode;
import com.github.pyro2266.bublight.modes.Color;
import com.github.pyro2266.bublight.modes.LedModeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SimpleColorMode implements BaseLedMode {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleColorMode.class);
    private static final String MODE_ID = "Simple";

    private SimpleColorModeConfig config;

    @Autowired
    public SimpleColorMode(BubLightConfiguration bubLightConfiguration) {

        Color[] colors = new Color[bubLightConfiguration.getLedCount()];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = new Color();
        }
        config = new SimpleColorModeConfig(colors);
    }

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
