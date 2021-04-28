package com.github.pyro2266.bublight.service.pressure.driver;

import java.io.IOException;
import org.springframework.stereotype.Service;

@Service
public interface PressureSensor {

    /**
     * Get current pressure
     */
    float readPressure() throws IOException, InterruptedException;

}
