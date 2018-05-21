package com.github.pyro2266.lightit.led;

import com.github.mbelling.ws281x.LedStrip;
import com.github.mbelling.ws281x.LedStripType;
import com.github.mbelling.ws281x.Ws281xLedStrip;
import com.github.pyro2266.lightit.led.modes.LedMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LedService {

    private static final Logger LOG = LoggerFactory.getLogger(LedService.class);
    public static final int LED_COUNT = 16;

    private LedStrip strip;
    private ColorLoop colorLoop;

    public LedService() {
        this.strip = new Ws281xLedStrip(LED_COUNT, 18, 800000, 10, 255, 0, false, LedStripType.WS2811_STRIP_GRB, true);

        // TODO tmp
        this.colorLoop = new ColorLoop();
    }

    public void renderColor(int red, int green, int blue) {
        LOG.info("Rendering RGB: {} {} {}", red, green, blue);
        strip.setStrip(red, green, blue);
        strip.render();
    }

    private void colorLoop() {

        Thread thread = new Thread();

    }

    private class ColorLoop implements Runnable {

        LedMode mode;


        ColorLoop(LedMode mode) {
            this.mode = mode;
        }

        @Override
        public void run() {

        }
    }
}
