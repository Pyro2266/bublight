package com.github.pyro2266.lightit.led.core;

import com.github.mbelling.ws281x.LedStrip;
import com.github.mbelling.ws281x.LedStripType;
import com.github.mbelling.ws281x.Ws281xLedStrip;
import com.github.pyro2266.lightit.led.modes.api.BaseLedMode;
import com.github.pyro2266.lightit.led.modes.api.LedModeException;
import com.github.pyro2266.lightit.led.modes.api.OverlayLedMode;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LedServiceImpl implements LedService {

    private static final Logger LOG = LoggerFactory.getLogger(LedServiceImpl.class);
    public static final int LED_COUNT = 16;

    private LedStrip strip;
    private Color[] colors;
    private BaseLedMode baseLedMode;
    private OverlayLedMode overlayLedMode;
    private AtomicInteger refreshRate = new AtomicInteger(20);

    private Thread colorLoopThread;
    private AtomicBoolean isRunning = new AtomicBoolean(false);
    private ReentrantLock modeLock = new ReentrantLock();

    public LedServiceImpl() {
        this.strip = new Ws281xLedStrip(LED_COUNT, 18, 800000, 10, 255, 0, false, LedStripType.WS2811_STRIP_GRB, true);

        this.colors = new Color[LedServiceImpl.LED_COUNT];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = new Color(55, 55, 55);
        }
    }

    @PostConstruct
    public void init() {
        startColorLoop();
    }

    @Override
    public void startColorLoop() {
        isRunning.set(true);
        colorLoopThread = new Thread( () -> {
            LOG.info("Starting color loop!");
            while (isRunning.get()) {

                modeLock.lock();
                try {
                    if (baseLedMode != null) {
                        colors = baseLedMode.getNextColors();
                    }
                    if (overlayLedMode != null) {
                        colors = overlayLedMode.getNextColors(colors);
                    }
                } catch (LedModeException e) {
                    LOG.error("Error while obtaining colors! Stopping loop!", e);
                    isRunning.set(false);
                } finally {
                    modeLock.unlock();
                }

                for (int i = 0; i < colors.length; i++) {
                    strip.setPixel(i, colors[i].getRed(), colors[i].getGreen(), colors[i].getBlue());
                }
                strip.render();

                try {
                    Thread.sleep(refreshRate.get());
                } catch (InterruptedException e) {
                    LOG.error("Error in core color loop!", e);
                    return;
                }
            }
            LOG.info("Color loop stopped!");
        } );
        colorLoopThread.start();
    }

    @Override
    public void stopColorLoop() {
        if (colorLoopThread != null) {
            isRunning.set(false);
            try {
                colorLoopThread.join();
            } catch (InterruptedException e) {
                LOG.error("Unable to stop core color loop!", e);
            }
        } else {
            LOG.warn("Color loop is not started!");
        }
    }

    @Override
    public void setRefreshRate(int refreshRate) {
        this.refreshRate.set(refreshRate);
    }

    @Override
    public void setBaseLedMode(BaseLedMode baseLedMode) {
        modeLock.lock();
        this.baseLedMode = baseLedMode;
        modeLock.unlock();
    }

    @Override
    public void setOverlayLedMode(OverlayLedMode overlayLedMode) {
        modeLock.lock();
        this.overlayLedMode = overlayLedMode;
        modeLock.unlock();
    }
}
