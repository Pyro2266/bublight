package com.github.pyro2266.bublight.service.colormodes;

import com.github.pyro2266.bublight.service.colormodes.data.BaseLedMode;
import com.github.pyro2266.bublight.service.colormodes.data.Color;
import com.github.pyro2266.bublight.service.colormodes.data.OverlayLedMode;

public interface ColorModesService {

    /**
     * Start color rendering loop.
     */
    void startColorLoop();

    /**
     * Stop color rendering loop.
     */
    void stopColorLoop();

    /**
     * Set rate in which are colors rendered.
     */
    void setRefreshRate(int refreshRate);

    /**
     * Set base mode.
     */
    void setBaseLedMode(BaseLedMode baseLedMode);

    /**
     * Set overlay mode.
     */
    void setOverlayLedMode(OverlayLedMode overlayLedMode);

    /**
     *
     * @return current base led mode
     */
    BaseLedMode getBaseLedMode();

    /**
     *
     * @return current overlay mode
     */
    OverlayLedMode getOverlayMode();

    /**
     *
     * @return {@link Boolean#TRUE TRUE} if led is running, {@link Boolean#FALSE FALSE} if not
     */
    boolean isRunning();

    /**
     * Disable overlay mode.
     */
    void disableOverlayMode();

    /**
     * Get current calculated colors
     */
    Color[] getCurrentColors();
}
