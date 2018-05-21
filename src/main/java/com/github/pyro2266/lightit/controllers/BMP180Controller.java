package com.github.pyro2266.lightit.controllers;

import com.github.pyro2266.lightit.pressure.PressureException;
import com.github.pyro2266.lightit.pressure.PressureService;
import com.github.pyro2266.lightit.led.LedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/bmp")
public class BMP180Controller {

    private static final Logger LOG = LoggerFactory.getLogger(BMP180Controller.class);

    @Autowired
    private PressureService pressureService;

    @Autowired
    private LedService ledService;

    @GetMapping(path = "/pressure")
    public ResponseEntity getPressure() {
        float pressure = 0;
        try {
            pressure = pressureService.getPressure();
        } catch (PressureException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to read pressure!");
        }
        LOG.info("Pressure is: {}", pressure);
        return ResponseEntity.ok(pressure);
    }

    @PostMapping(path = "/calibratePressure")
    public ResponseEntity calibratePressure() {
        try {
            pressureService.calibrateNormalPressure();
            return ResponseEntity.ok().build();
        } catch (PressureException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to calibrate!");
        }
    }

    @PostMapping(path = "/ledByPressure")
    public ResponseEntity ledByPressure() {
        // TODO fix this blocking call...
        try {
            pressureService.calibrateNormalPressure();
            for (int i = 0; i < 1000; i++) {
                float pressureDif = pressureService.getPressureDifference();
                int color = (int) (150 + (pressureDif/2000) * 100);
                if (color > 255) color = 255;
                if (color < 0) color = 0;
                LOG.info("Interation: {}     Color: {}", i, color);
                ledService.renderColor(0, 0, color);
                Thread.sleep(25);
            }
            return ResponseEntity.ok().build();
        } catch (PressureException | InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
