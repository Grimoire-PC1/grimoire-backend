package com.grimoire.dto.engine;

import com.grimoire.dto.characterSheetTemplate.CharacterSheetSubTabTypeEnum;
import lombok.Getter;

import java.security.InvalidParameterException;
import java.util.Objects;

@Getter
public enum EngineTypeEnum {
    PUBLICO(1L, "PUBLICO"),
    PRIVADO(2L, "PRIVADO");

    private final Long id;
    private final String description;

    EngineTypeEnum(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public static EngineTypeEnum fromId(Long id) {
        for (EngineTypeEnum type : values()) {
            if (type.getId().equals(id)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid Engine Type Enum ID: " + id);
    }
}
