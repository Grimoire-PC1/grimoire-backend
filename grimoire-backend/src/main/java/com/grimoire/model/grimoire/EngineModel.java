package com.grimoire.model.grimoire;

import com.grimoire.dto.engine.EngineResponseDto;
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

    @Column(name = "ID_CRIADOR", nullable = false)
    private Long idUser;

    @Column(name = "NOME", nullable = false)
    private String name;

    @Column(name = "DESCRICAO", nullable = false)
    private String description;

    @Column(name = "URL_FOTO")
    private String pictureUrl;

    @Column(name = "TIPO_SISTEMA", nullable = false)
    private String typeSys; //É para ser Enum

    public EngineResponseDto toDto() {
        return EngineResponseDto.builder()
                .idUser(this.idUser)
                .name(this.name)
                .description(this.description)
                .pictureUrl(this.pictureUrl)
                .typeSys(this.typeSys)
                .build();
    }
}
