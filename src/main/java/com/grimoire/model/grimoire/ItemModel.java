package com.grimoire.model.grimoire;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "ARQUIVOS")
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
    private String diceQuantity;

    @Column(name = "TIPO_DADO")
    private String diceType;

    @Column(name = "BONUS_DADO")
    private String diceBonus;

    @Column(name = "ROLAR_DADO")
    private String rollDice;

    @Column(name = "QUANTIDADE")
    private String itemQuantity;
}
