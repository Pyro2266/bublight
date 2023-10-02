package com.github.pyro2266.bublight.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "bublight")
public class BubLightConfiguration {

    private int ledCount;
    @Deprecated
    private boolean simulatedPressure;
    private boolean simulatedSensor;
    private boolean simulatedLed;

    public int getLedCount() {
        return ledCount;
    }

    public void setLedCount(int ledCount) {
        this.ledCount = ledCount;
    }

    @Deprecated
    public boolean isSimulatedPressure() {
        return simulatedPressure;
    }

    public void setSimulatedPressure(final boolean simulatedPressure) {
        this.simulatedPressure = simulatedPressure;
    }

    public boolean isSimulatedLed() {
        return simulatedLed;
    }

    public void setSimulatedLed(final boolean simulatedLed) {
        this.simulatedLed = simulatedLed;
    }

    public boolean isSimulatedSensor() {
        return simulatedSensor;
    }

    public void setSimulatedSensor(final boolean simulatedSensor) {
        this.simulatedSensor = simulatedSensor;
    }
}

