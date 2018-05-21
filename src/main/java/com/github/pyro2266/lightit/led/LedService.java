package com.github.pyro2266.lightit.led;

import com.github.mbelling.ws281x.Color;
import com.github.mbelling.ws281x.LedStrip;
import com.github.mbelling.ws281x.LedStripType;
import com.github.mbelling.ws281x.Ws281xLedStrip;
import com.github.pyro2266.lightit.led.modes.LedMode;
import com.github.pyro2266.lightit.led.modes.SimpleRainbowMode;
import com.github.pyro2266.lightit.pressure.PressureException;
import com.github.pyro2266.lightit.pressure.PressureService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LedService {

    private static final Logger LOG = LoggerFactory.getLogger(LedService.class);
    public static final int LED_COUNT = 16;

    @Autowired
    private PressureService pressureService;

    private LedStrip strip;
    private ColorLoop colorLoop;
    private Thread colorLoopThread;
    private AtomicInteger refreshRate = new AtomicInteger(20);

    // TODO
    private int pressureRange = 200;
    private int defaultBrightness = 50;

    public LedService() {
        this.strip = new Ws281xLedStrip(LED_COUNT, 18, 800000, 10, 255, 0, false, LedStripType.WS2811_STRIP_GRB, true);

        // TODO tmp
        SimpleRainbowMode simpleRainbowMode = new SimpleRainbowMode();
        this.colorLoop = new ColorLoop(simpleRainbowMode);
    }

    public void renderColor(int red, int green, int blue) {
        LOG.info("Rendering RGB: {} {} {}", red, green, blue);
        strip.setStrip(red, green, blue);
        strip.render();
    }

    public void startColorLoop() {
        colorLoop.startLoop();
        colorLoopThread = new Thread(colorLoop);
        colorLoopThread.start();
    }

    public void stopColorLoop() {
        if (colorLoopThread != null) {
            colorLoop.stopLoop();
            try {
                colorLoopThread.join();
            } catch (InterruptedException e) {
                // TODO error handling
                LOG.error("Unable to stop color loop", e);
            }
        } else {
            LOG.warn("Color loop is not started!");
        }
    }

    public void setRefreshRate(int refreshRate) {
        this.refreshRate.set(refreshRate);
    }

    public void setPressureRange(int pressureRange) {
        this.pressureRange = pressureRange;
    }

    public void setDefaultBrightness(int defaultBrightness) {
        this.defaultBrightness = defaultBrightness;
    }

    private class ColorLoop implements Runnable {
        Color[] colors;
        LedMode mode;
        AtomicBoolean stop = new AtomicBoolean(false);

        ColorLoop(LedMode mode) {
            this.mode = mode;
            this.colors = new Color[LedService.LED_COUNT];
            for (int i = 0; i < colors.length; i++) {
                colors[i] = new Color(0, 0, 0);
            }
        }

        @Override
        public void run() {
            LOG.info("Starting color loop!");
            while (!stop.get()) {
                colors = mode.getNextColors(colors);


                // TODO apply overlay

                // TODO tmp
                int brightness;
                try {
                    float pressureDif = pressureService.getPressureDifference();
                    brightness = (int) (defaultBrightness + (pressureDif / pressureRange) * 100);
                    if (brightness > 255) brightness = 255;
                    if (brightness < 0) brightness = 0;
                    LOG.info("Brightness: {}", brightness);
                } catch (PressureException e) {
                    LOG.error("Unable to set brightness", e);
                    brightness = 255;
                }

                for (int i = 0; i < colors.length; i++) {
                    strip.setPixel(i, colors[i]);
                }
                strip.setBrightness(brightness);
                strip.render();
                LOG.info("Rendered with brightness {}", brightness);

                try {
                    Thread.sleep(refreshRate.get());
                } catch (InterruptedException e) {
                    // TODO error handling
                    LOG.error("Some error", e);
                }
            }
            LOG.info("Color loop stopped!");
        }

        void stopLoop() {
            stop.set(true);
        }

        void startLoop() {
            stop.set(false);
        }
    }
}
