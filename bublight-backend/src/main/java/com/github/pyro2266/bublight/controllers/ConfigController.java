package com.github.pyro2266.bublight.controllers;

import com.github.pyro2266.bublight.service.config.ConfigService;
import com.github.pyro2266.bublight.service.config.data.CurrentConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/config")
@CrossOrigin(origins = "*")
public class ConfigController {

    ConfigService configService;

    @Autowired
    public ConfigController(ConfigService configService) {
        this.configService = configService;
    }

    @GetMapping( path = "/current")
    public CurrentConfig getCurrentConfig(){
        return configService.getCurrentConfig();
    }
}
