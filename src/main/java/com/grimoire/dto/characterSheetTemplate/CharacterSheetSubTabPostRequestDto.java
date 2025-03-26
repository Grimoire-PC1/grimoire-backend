package com.grimoire.dto.characterSheetTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CharacterSheetSubTabPostRequestDto {
    @JsonProperty("novo_nome")
    private String name;
}
