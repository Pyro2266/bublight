package com.github.pyro2266.lightit.services;

import com.github.mbelling.ws281x.LedStripType;
import com.github.mbelling.ws281x.Ws281xLedStrip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LedService {

    private static final Logger LOG = LoggerFactory.getLogger(LedService.class);
    private Ws281xLedStrip strip;

    public LedService() {
        strip = new Ws281xLedStrip(16, 18, 800000, 10, 255, 0, false, LedStripType.WS2811_STRIP_GRB, true);
    }

    public void renderColor(int red, int green, int blue) {
        LOG.info("Rendering RGB: {} {} {}", red, green, blue);
        strip.setStrip(red, green, blue);
        strip.render();
    }
}
