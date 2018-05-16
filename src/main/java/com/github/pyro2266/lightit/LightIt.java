package com.github.pyro2266.lightit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LightIt {

    private static final Logger LOG = LoggerFactory.getLogger(LightIt.class);

    public static void main(String[] args) {
        SpringApplication.run(LightIt.class, args);
    }

}
