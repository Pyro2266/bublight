package com.github.pyro2266.lightit.controllers;

import com.github.pyro2266.lightit.services.LedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/led")
public class LedController {

    private static final Logger LOG = LoggerFactory.getLogger(LedController.class);

    @Autowired
    private LedService ledService;

    @GetMapping(path = "/hello")
    public ResponseEntity hello() {
        LOG.info("hello!");
        return ResponseEntity.ok("hello!");
    }

    @PostMapping(path = "/{red}/{green}/{blue}")
    public ResponseEntity turnOn(@PathVariable int red, @PathVariable int green, @PathVariable int blue) {
        ledService.renderColor(red, green, blue);
        return ResponseEntity.ok().build();
    }

}
