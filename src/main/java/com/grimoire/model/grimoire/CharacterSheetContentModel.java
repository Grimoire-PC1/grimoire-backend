package com.grimoire.model.grimoire;

import com.grimoire.dto.characterSheetContent.CharacterSheetContentResponseDto;
import com.grimoire.dto.characterSheetTemplate.CharacterSheetSubTabTypeEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "SUB_ABA_FICHA_CONTEUDO")
@EqualsAndHashCode
public class CharacterSheetContentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="ID_PERSONAGEM")
    private CharacterModel characterModel;

    @ManyToOne
    @JoinColumn(name="ID_TEMPLATE_SUB_ABA_FICHA")
    private CharacterSheetSubTabModel subTabModel;

    @Column(name = "TEXTO")
    private String text;

    @Column(name = "INTEIRO")
    private Integer number;

    @Column(name = "QTD_DADOS")
    private Integer diceQuantity;

    @Column(name = "TIPO_DADO")
    private Integer diceType;

    @Column(name = "BONUS_DADO")
    private Integer diceBonus;

    public CharacterSheetContentResponseDto toDto() {
        CharacterSheetContentResponseDto dto = CharacterSheetContentResponseDto.builder()
                .id(this.id)
                .idCharacter(this.characterModel.getId())
                .idCharacterTab(this.subTabModel.getId())
                .name(this.subTabModel.getName())
                .type(this.subTabModel.getSubTabTypeModel().getDescription())
                .build();

        switch (CharacterSheetSubTabTypeEnum.fromId(this.subTabModel.getSubTabTypeModel().getId())) {
            case CharacterSheetSubTabTypeEnum.TEXTO:
                dto.setContent(List.of(this.text));
                break;
            case CharacterSheetSubTabTypeEnum.INTEIRO:
                dto.setContent(List.of(String.valueOf(this.number)));
                break;
            case CharacterSheetSubTabTypeEnum.DADO:
                dto.setContent(List.of(
                        String.valueOf(this.diceQuantity),
                        String.valueOf(this.diceType),
                        String.valueOf(this.diceBonus)
                ));
                break;
        }
        return dto;
    }
}
