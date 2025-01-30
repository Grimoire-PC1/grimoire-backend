package com.grimoire.controller;

import com.grimoire.controller.documentation.PrivateController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private")
public class PrivateControllerImpl implements PrivateController {

    @GetMapping
    public String getMessage() {
        return "Hello World";
    }
}
