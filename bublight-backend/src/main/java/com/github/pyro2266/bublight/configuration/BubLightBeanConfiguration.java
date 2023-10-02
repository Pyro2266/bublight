package com.github.pyro2266.bublight.configuration;

import com.github.pyro2266.bublight.service.ledcore.LedRendererService;
import com.github.pyro2266.bublight.service.ledcore.impl.LedRendererServiceImpl;
import com.github.pyro2266.bublight.service.ledcore.impl.LedRendererServiceSimulatedImpl;
import com.github.pyro2266.bublight.service.sensor.driver.BMP180Driver;
import com.github.pyro2266.bublight.service.sensor.driver.Sensor;
import com.github.pyro2266.bublight.service.sensor.driver.SensorSimulatedImpl;
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
    public Sensor sensor() {
        if (bubLightConfiguration.isSimulatedPressure()) {
            return new SensorSimulatedImpl(0, 0);
        } else {
            return new BMP180Driver();
        }
    }

}
