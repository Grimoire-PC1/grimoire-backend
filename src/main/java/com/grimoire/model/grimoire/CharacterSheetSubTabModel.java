package com.grimoire.model.grimoire;

import com.grimoire.dto.characterSheetTemplate.CharacterSheetSubTabResponseDto;
import com.grimoire.model.grimoire.typeTables.CharacterSheetSubTabTypeModel;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "TEMPLATES_SUB_ABA_FICHA")
@EqualsAndHashCode
public class CharacterSheetSubTabModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="ID_ABA_FICHA")
    private CharacterSheetTabModel characterSheetTabModel;

    @ManyToOne
    @JoinColumn(name="ID_TIPO_SUB_ABA")
    private CharacterSheetSubTabTypeModel subTabTypeModel;

    @Column(name = "NOME")
    private String name;

    public CharacterSheetSubTabResponseDto toDto() {
        return CharacterSheetSubTabResponseDto.builder()
                .id(this.id)
                .idCharacterTab(this.characterSheetTabModel.getId())
                .idEngine(this.characterSheetTabModel.getEngine().getId())
                .idEngineOwner(this.characterSheetTabModel.getEngine().getOwner().getId())
                .name(this.name)
                .type(this.subTabTypeModel.getDescription())
                .build();
    }
}
