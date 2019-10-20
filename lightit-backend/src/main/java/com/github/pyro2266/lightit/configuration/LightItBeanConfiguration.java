package com.github.pyro2266.lightit.configuration;

import com.github.pyro2266.lightit.led.core.LedRendererService;
import com.github.pyro2266.lightit.led.core.LedRendererServiceImpl;
import com.github.pyro2266.lightit.led.core.LedRendererServiceSimulatedImpl;
import com.github.pyro2266.lightit.pressure.OnPressureDiffReadEvent;
import com.github.pyro2266.lightit.pressure.OnPressureDiffReadEventPushWebsocket;
import com.github.pyro2266.lightit.pressure.PressureService;
import com.github.pyro2266.lightit.pressure.PressureServiceImpl;
import com.github.pyro2266.lightit.pressure.driver.BMP180Driver;
import com.github.pyro2266.lightit.pressure.driver.PressureSensor;
import com.github.pyro2266.lightit.pressure.driver.PressureSensorSimulatedImpl;
import com.github.pyro2266.lightit.websocket.PressureWebsocketService;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LightItBeanConfiguration {

    private LightItConfiguration lightItConfiguration;

    @Autowired
    public LightItBeanConfiguration(LightItConfiguration lightItConfiguration) {
        this.lightItConfiguration = lightItConfiguration;
    }

    @Bean
    public LedRendererService ledRendererService() {
        if (lightItConfiguration.isSimulatedMode()) {
            return new LedRendererServiceSimulatedImpl();
        } else {
            return new LedRendererServiceImpl(lightItConfiguration);
        }
    }

    @Bean
    public PressureSensor pressureSensor() {
        if (lightItConfiguration.isSimulatedMode()) {
            return new PressureSensorSimulatedImpl(1000);
        } else {
            return new BMP180Driver();
        }
    }

    @Bean
    @Autowired
    public PressureService pressureService(PressureSensor pressureSensor,
            PressureWebsocketService pressureWebsocketService) {
        OnPressureDiffReadEvent onPressureDiffReadEvent = new OnPressureDiffReadEventPushWebsocket(
                pressureWebsocketService);
        return new PressureServiceImpl(pressureSensor, Collections.singleton(onPressureDiffReadEvent));
    }
}