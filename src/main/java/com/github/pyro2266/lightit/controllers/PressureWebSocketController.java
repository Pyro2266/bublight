package com.github.pyro2266.lightit.controllers;

import com.github.pyro2266.lightit.pressure.PressureException;
import com.github.pyro2266.lightit.pressure.PressureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class PressureWebSocketController {

    private static final Logger LOG = LoggerFactory.getLogger(PressureController.class);

    private final SimpMessagingTemplate template;

    private PressureService pressureService;
    
    @Autowired
    public PressureWebSocketController(SimpMessagingTemplate template, PressureService pressureService) {
        this.template = template;
        this.pressureService = pressureService;
        
    }
    
    @MessageMapping("/pressure")
    public void onReciveMessage(String message) {
        float pressure = 0;
        try {
            pressure = pressureService.getPressure();
        } catch (PressureException e) {
            this.template.convertAndSend("/pressureRes", "Unable to read pressure!");
        }
        LOG.info("Pressure is: {}", pressure);
        this.template.convertAndSend("/pressureRes", pressure);
    }
}
