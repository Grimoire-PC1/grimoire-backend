package com.grimoire.controller.documentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "nome", description = "descricao")
public interface PrivateController {
    @Operation(description = "descricao", summary = "resumo")
    public String getMessage();
}
