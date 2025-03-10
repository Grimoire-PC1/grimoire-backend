package com.grimoire.model.grimoire;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.grimoire.dto.item.ItemResponseDto;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "ITENS")
@EqualsAndHashCode
public class ItemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_CAMPANHA", nullable = false)
    private CampaignModel campaign;

    @Column(name = "NOME")
    private String name;

    @Column(name = "DESCRICAO")
    private String description;

    @Column(name = "QTD_DADOS")
    private Integer diceQuantity;

    @Column(name = "TIPO_DADO")
    private Integer diceType;

    @Column(name = "BONUS_DADO")
    private Integer diceBonus;

    @Column(name = "ROLAR_DADO")
    private String rollDice;

    @Column(name = "QUANTIDADE")
    private Integer itemQuantity;

    public ItemResponseDto toDto() {
        return ItemResponseDto.builder()
                .id(this.id)
                .idCampaign(this.campaign.getId())
                .idCampaignMaster(this.campaign.getOwner().getId())
                .name(this.name)
                .description(this.description)
                .diceQuantity(this.diceQuantity)
                .diceType(this.diceType)
                .diceBonus(this.diceBonus)
                .rollDice(this.rollDice)
                .itemQuantity(this.itemQuantity)
                .build();
    }
}
