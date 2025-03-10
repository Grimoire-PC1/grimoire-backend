package com.grimoire.dto.characterSheetTemplate;

import com.grimoire.dto.campaignPackage.CampaignFileTypeEnum;
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

    public static CharacterSheetSubTabTypeEnum fromId(Long id) {
        for (CharacterSheetSubTabTypeEnum type : values()) {
            if (type.getId().equals(id)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid Character Sheet Sub Tab Type ID: " + id);
    }
}
