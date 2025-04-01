package com.grimoire.controller;

import com.grimoire.controller.documentation.ServerController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/server")
@Validated
@CrossOrigin
@Slf4j
public class ServerControllerImpl implements ServerController {

    @GetMapping("/ping")
    public String ping() {
        return "O servidor está funcionando. Tamo junto.";
    }
}
