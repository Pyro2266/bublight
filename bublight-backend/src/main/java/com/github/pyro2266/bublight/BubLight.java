package com.github.pyro2266.bublight;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BubLight {

    private static final Logger LOG = LoggerFactory.getLogger(BubLight.class);

    public static void main(String[] args) {
        SpringApplication.run(BubLight.class, args);
    }

}
