package com.github.pyro2266.lightit.led.modes;

import com.github.mbelling.ws281x.Color;

public interface LedMode {

    String getModeId();

    Color[] getNextColors(Color[] previousColors);

}
