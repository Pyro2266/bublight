package com.github.pyro2266.bublight.configuration;

import com.github.pyro2266.bublight.service.ledcore.LedRendererService;
import com.github.pyro2266.bublight.service.ledcore.impl.LedRendererServiceImpl;
import com.github.pyro2266.bublight.service.ledcore.impl.LedRendererServiceSimulatedImpl;
import com.github.pyro2266.bublight.service.pressure.driver.BMP180Driver;
import com.github.pyro2266.bublight.service.pressure.driver.PressureSensor;
import com.github.pyro2266.bublight.service.pressure.driver.PressureSensorSimulatedImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BubLightBeanConfiguration {

    private final BubLightConfiguration bubLightConfiguration;

    @Autowired
    public BubLightBeanConfiguration(BubLightConfiguration bubLightConfiguration) {
        this.bubLightConfiguration = bubLightConfiguration;
    }

    @Bean
    public LedRendererService ledRendererService() {
        if (bubLightConfiguration.isSimulatedLed()) {
            return new LedRendererServiceSimulatedImpl();
        } else {
            return new LedRendererServiceImpl(bubLightConfiguration);
        }
    }

    @Bean
    public PressureSensor pressureSensor() {
        if (bubLightConfiguration.isSimulatedPressure()) {
            return new PressureSensorSimulatedImpl(0);
        } else {
            return new BMP180Driver();
        }
    }

}
