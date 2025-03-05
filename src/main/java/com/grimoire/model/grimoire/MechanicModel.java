package com.grimoire.model.grimoire;

import com.grimoire.dto.mechanic.MechanicResponseDto;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "MECANICAS")
@EqualsAndHashCode
public class MechanicModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="ID_SISTEMA")
    private EngineModel engine;

    @Column(name = "NOME")
    private String name;

    @Column(name = "DESCRICAO")
    private String description;

    @Column(name = "ACOES")
    private String actions;

    @Column(name = "EFEITOS")
    private String effects;

    public MechanicResponseDto toDto() {
        return MechanicResponseDto.builder()
                .id(this.id)
                .idEngine(this.engine.getId())
                .idEngineOwner(this.engine.getOwner().getId())
                .name(this.name)
                .description(this.description)
                .actions(this.actions)
                .effects(this.effects)
                .build();
    }
}
