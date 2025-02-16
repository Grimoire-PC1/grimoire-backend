package com.grimoire.dto.engine;

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

    public static String getEngineTypeByCode(Long code) {
        if (code == null) {
            return null;
        }
        for (EngineTypeEnum type : EngineTypeEnum.values()) {
            if (Objects.equals(type.getId(), code)) {
                return type.getDescription().toUpperCase();
            }
        }
        throw new InvalidParameterException("Tipo de Sistema Inválido: " + code);
    }
}
