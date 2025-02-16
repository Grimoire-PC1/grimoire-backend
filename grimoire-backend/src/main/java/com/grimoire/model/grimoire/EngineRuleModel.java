package com.grimoire.model.grimoire;


import com.grimoire.dto.engineRule.RuleResponseDto;
import com.grimoire.model.grimoire.UserModel;
import com.grimoire.model.grimoire.EngineModel;
import com.grimoire.model.joinTables.EngineTypeModel;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "REGRAS_SISTEMA")
@EqualsAndHashCode
public class EngineRuleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="ID_SISTEMA")
    private EngineModel engine;

    @Column(name = "TITULO", nullable = false)
    private String title;

    @Column(name = "DESCRICAO", nullable = false)
    private String description;

    public RuleResponseDto toDto() {
        return RuleResponseDto.builder()
                .title(this.title)
                .description(this.description)
                .build();
    }
}
