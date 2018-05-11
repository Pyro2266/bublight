package com.github.pyro2266.lightit;

import com.github.mbelling.ws281x.Color;
import com.github.mbelling.ws281x.Ws281xLedStrip;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger LOG = LogManager.getLogger( Main.class );

    public static void main(String[] args) {
        Ws281xLedStrip strip = new Ws281xLedStrip(
                16,       // leds
                18,          // Using pin 10 to do SPI, which should allow non-sudo access
                800000,  // freq hz
                10,            // dma
                255,      // brightness
                0,      // pwm channel
                false,        // invert
                1050624);
        strip.setStrip(Color.CYAN);
        strip.render();

    }

}
