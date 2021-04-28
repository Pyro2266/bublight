package com.github.pyro2266.bublight.service.config.data;

import com.github.pyro2266.bublight.service.colormodes.data.BaseLedMode;
import com.github.pyro2266.bublight.service.colormodes.data.OverlayLedMode;

public class CurrentConfig {

    private BaseLedMode baseMode;
    private OverlayLedMode overlayMode;
    private boolean led;

    public CurrentConfig(BaseLedMode baseMode, OverlayLedMode overlayMode, boolean led) {
        this.baseMode = baseMode;
        this.overlayMode = overlayMode;
        this.led = led;
    }

    public BaseLedMode getBaseMode() {
        return baseMode;
    }

    public void setBaseMode(BaseLedMode baseMode) {
        this.baseMode = baseMode;
    }

    public OverlayLedMode getOverlayMode() {
        return overlayMode;
    }

    public void setOverlayMode(OverlayLedMode overlayMode) {
        this.overlayMode = overlayMode;
    }

    public boolean isLed() {
        return led;
    }

    public void setLed(boolean led) {
        this.led = led;
    }
}
