package com.grimoire.dto.characterSheetContent;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CharacterSheetContentCreateRequestDto {
    @JsonProperty("conteudo")
    private List<String> content;
}
