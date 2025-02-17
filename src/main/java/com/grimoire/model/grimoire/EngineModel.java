package com.grimoire.model.grimoire;

import com.grimoire.dto.engine.EngineResponseDto;
import com.grimoire.model.joinTables.EngineTypeModel;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "SISTEMAS")
@EqualsAndHashCode
public class EngineModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_CRIADOR", nullable = false)
    private UserModel creator;

    @Column(name = "NOME", nullable = false)
    private String name;

    @Column(name = "DESCRICAO", nullable = false)
    private String description;

    @Column(name = "URL_FOTO")
    private String pictureUrl;

    @ManyToOne
    @JoinColumn(name="ID_TIPO_SISTEMA")
    private EngineTypeModel engineType;

    public EngineResponseDto toDto() {
        return EngineResponseDto.builder()
                .id(this.id)
                .idUser(this.creator.getId())
                .name(this.name)
                .description(this.description)
                .pictureUrl(this.pictureUrl)
                .typeSys(this.engineType.getDescription())
                .build();
    }
}
