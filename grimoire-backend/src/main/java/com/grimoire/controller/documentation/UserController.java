package com.grimoire.controller.documentation;

import com.grimoire.dto.user.UserCreateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Usuário", description = "Serviço de Usuário")
public interface UserController {
    @Operation(description = "Registrar usuário")
    ResponseEntity<String> createUser(@Validated @RequestBody UserCreateRequestDto userDto);
}
