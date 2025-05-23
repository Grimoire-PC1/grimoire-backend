package com.grimoire.controller.documentation;

import com.grimoire.dto.user.UserCreateRequestDto;
import com.grimoire.dto.user.UserPostRequestDto;
import com.grimoire.dto.user.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Usuário", description = "Serviço de Usuário")
public interface UserController {
    @Operation(description = "Registrar usuário", summary = "Registrar novo usuário no sistema")
    ResponseEntity<String> createUser(
            @RequestBody UserCreateRequestDto userDto);

    @Operation(description = "Atualizar usuário", summary = "Atualizar usuário no sistema. Deixe o campo vazio para mantê-lo.")
    ResponseEntity<String> updateUser(
            @RequestBody() UserPostRequestDto userDto,
            Authentication authentication);

    @Operation(description = "Deletar usuário", summary = "Remover usuário do sistema.")
    ResponseEntity<String> deleteUser(Authentication authentication);

    @Operation(description = "Ler usuário", summary = "Pegar informações do usuário no sistema.")
    ResponseEntity<UserResponseDto> getUser(Authentication authentication);
}
