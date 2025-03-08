package com.grimoire.dto.campaignPackage;

import lombok.Getter;

import java.security.InvalidParameterException;
import java.util.Objects;

@Getter
public enum CampaignFileTypeEnum {
    TEXTO(1L, "TEXTO"),
    IMAGEM(2L, "IMAGEM"),
    ITEM(3L, "ITEM"),
    PERSONAGEM(4L, "PERSONAGEM");

    private final Long id;
    private final String description;

    CampaignFileTypeEnum(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public static String getTypeByCode(Long code) {
        if (code == null) {
            return null;
        }
        for (com.grimoire.dto.session.SessionTypeEnum type : com.grimoire.dto.session.SessionTypeEnum.values()) {
            if (Objects.equals(type.getId(), code)) {
                return type.getDescription().toUpperCase();
            }
        }
        throw new InvalidParameterException("Tipo de Arquivo Inválido: " + code);
    }
}
