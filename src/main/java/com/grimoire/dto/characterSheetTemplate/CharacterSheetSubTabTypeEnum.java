package com.grimoire.dto.characterSheetTemplate;

import lombok.Getter;

@Getter
public enum CharacterSheetSubTabTypeEnum {
    TEXTO(1L, "TEXTO"),
    INTEIRO(2L, "INTEIRO"),
    DADO(3L, "DADO");

    private final Long id;
    private final String description;

    CharacterSheetSubTabTypeEnum(Long id, String description) {
        this.id = id;
        this.description = description;
    }
}
