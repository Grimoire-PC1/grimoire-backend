package com.grimoire.model.joinTables;


import com.grimoire.dto.engineRule.RuleResponseDto;
import com.grimoire.model.grimoire.UserModel;
import com.grimoire.model.grimoire.EngineModel;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "REGRA_SISTEMA")
@EqualsAndHashCode
public class EngineRuleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserModel user;

    @ManyToOne
    private EngineModel engine;

    @Column(name = "ID_USUARIO", nullable = false)
    private Long idUser = user.getId();

    @Column(name = "ID_SISTEMA", nullable = false)
    private Long idSys = engine.getId();

    @Column(name = "TITULO", nullable = false)
    private String title;

    @Column(name = "DESCRICAO", nullable = false)
    private String description;

    @Column(name = "TIPO", nullable = false)
    private String type; //É para ser Enum

    public RuleResponseDto toDto() {
        return RuleResponseDto.builder()
                .idUser(this.idUser)
                .idSys(this.idSys)
                .title(this.title)
                .description(this.description)
                .type(this.type)
                .build();
    }
}
