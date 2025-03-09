package com.grimoire.controller.documentation;

import com.grimoire.dto.item.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

@Tag(name = "Item", description = "Serviço de Itens de Campanha")
public interface ItemController {

    @Operation(description = "Registrar Item", summary = "Registrar novo Item de Campanha no Grimoire")
    ResponseEntity<ItemResponseDto> create(
            @Parameter(
                    name = "id_campanha",
                    description = "ID da Campanha de RPG.",
                    required = true
            )
            Long campaignId,
            @Validated @RequestBody ItemCreateRequestDto itemDto,
            Authentication authentication);

    @Operation(description = "Atualizar Item", summary = "Atualizar Item de Campanha no Grimoire")
    ResponseEntity<ItemResponseDto> update(
            @Parameter(
                    name = "id_item",
                    description = "ID do Item de RPG.",
                    required = true
            )
            Long itemId,
            @Validated @RequestBody ItemPostRequestDto itemDto,
            Authentication authentication);

    @Operation(description = "Remover Item", summary = "Remover Item de Campanha no Grimoire.")
    ResponseEntity<String> delete(
            @Parameter(
                    name = "id_item",
                    description = "ID do Item de RPG.",
                    required = true
            )
            Long itemId,
            Authentication authentication);

    @Operation(description = "Pegar Itens", summary = "Pegar informações de Itens de Campanha no Grimoire.")
    ResponseEntity<Collection<ItemResponseDto>> getByCampaign(
            @Parameter(
                    name = "id_campanha",
                    description = "ID da Campanha de RPG.",
                    required = true
            )
            Long campaignId,
            Authentication authentication);
}
