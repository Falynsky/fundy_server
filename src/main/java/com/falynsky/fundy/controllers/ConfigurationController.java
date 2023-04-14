package com.falynsky.fundy.controllers;

import com.falynsky.fundy.utils.ResponseUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
public class ConfigurationController {

    @GetMapping("/isAlive")
    public ResponseEntity<Map<String, Object>> isAlive() {
        return ResponseUtils.sendCorrectResponse();
    }

}
