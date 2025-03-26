package com.grimoire.dto.session;

import lombok.Getter;

import java.security.InvalidParameterException;
import java.util.Objects;

@Getter
public enum SessionTypeEnum {
    FUTURA(1L, "FUTURA"),
    PASSADA(2L, "PASSADA");

    private final Long id;
    private final String description;

    SessionTypeEnum(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public static String getSessionTypeByCode(Long code) {
        if (code == null) {
            return null;
        }
        for (SessionTypeEnum type : SessionTypeEnum.values()) {
            if (Objects.equals(type.getId(), code)) {
                return type.getDescription().toUpperCase();
            }
        }
        throw new InvalidParameterException("Tipo de Sessão Inválido: " + code);
    }
}
