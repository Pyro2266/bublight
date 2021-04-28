package com.github.pyro2266.bublight.service.ledcore;

import com.github.pyro2266.bublight.service.colormodes.data.Color;
import org.springframework.stereotype.Service;

@Service
public interface LedRendererService {

    /**
     * Render colors on LED strip.
     */
    void renderColors(Color[] colors);

}
