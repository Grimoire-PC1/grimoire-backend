package com.grimoire.controller.documentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;

@Tag(name = "Servidor", description = "Serviço para servidor")
public interface ServerController {

    @Operation(description = "Checar funcionamento do servidor.", summary = "Checar funcionamento do servidor.")
    public String ping();
}
