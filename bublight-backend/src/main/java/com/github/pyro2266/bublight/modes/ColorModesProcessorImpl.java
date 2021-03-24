package com.github.pyro2266.bublight.modes;

import com.github.pyro2266.bublight.configuration.BubLightConfiguration;
import com.github.pyro2266.bublight.led.core.LedRendererService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ColorModesProcessorImpl implements ColorModesProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(ColorModesProcessorImpl.class);

    private final LedRendererService ledRenderer;
    private final BubLightConfiguration bubLightConfiguration;

    private Color[] colors;
    private BaseLedMode baseLedMode;
    private OverlayLedMode overlayLedMode;
    private AtomicInteger refreshRate = new AtomicInteger(20);

    private Thread colorLoopThread;
    private AtomicBoolean isRunning = new AtomicBoolean(false);
    private ReentrantLock modeLock = new ReentrantLock();

    private Color[] currentColors;

    @Autowired
    public ColorModesProcessorImpl(LedRendererService ledRenderer, BubLightConfiguration bubLightConfiguration) {
        this.ledRenderer = ledRenderer;
        this.bubLightConfiguration = bubLightConfiguration;
        this.colors = new Color[this.bubLightConfiguration.getLedCount()];

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
        if( !isRunning.compareAndSet( false, true) ){
            LOG.info( "Loop is already running" );
            return;
        }

        colorLoopThread = getNewColorLoopThread();
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

    @Override
    public synchronized Color[] getCurrentColors() {
        return currentColors;
    }

    private synchronized void storeCurrentColors(Color[] currentColors) {
        this.currentColors = currentColors.clone();
    }

    private Thread getNewColorLoopThread(){
        return new Thread( () -> {
            LOG.info("Starting color loop!");
            while (isRunning.get()) {

                modeLock.lock();
                try {
                    if (baseLedMode != null) {
                        colors = baseLedMode.getNextColors();
                    }
                    if (overlayLedMode != null) {
                        colors = overlayLedMode.getNextColors(colors.clone());
                    }
                } catch (LedModeException e) {
                    LOG.error("Error while obtaining colors! Stopping loop!", e);
                    isRunning.set(false);
                } finally {
                    modeLock.unlock();
                }

                storeCurrentColors(colors);
                ledRenderer.renderColors(colors);

                try {
                    Thread.sleep(refreshRate.get());
                } catch (InterruptedException e) {
                    LOG.error("Error in core color loop!", e);
                    return;
                }
            }
            LOG.info("Color loop stopped!");
        } );
    }
}
