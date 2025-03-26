package com.grimoire.model.grimoire;

import com.grimoire.dto.characterSheetTemplate.CharacterSheetTabResponseDto;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "TEMPLATES_ABA_FICHA")
@EqualsAndHashCode
public class CharacterSheetTabModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="ID_SISTEMA")
    private EngineModel engine;

    @Column(name = "NOME")
    private String name;

    public CharacterSheetTabResponseDto toDto() {
        return CharacterSheetTabResponseDto.builder()
                .id(this.id)
                .idEngine(this.engine.getId())
                .idEngineOwner(this.engine.getOwner().getId())
                .name(this.name)
                .build();
    }
}
