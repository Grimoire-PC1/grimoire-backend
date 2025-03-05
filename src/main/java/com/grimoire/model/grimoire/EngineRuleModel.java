package com.grimoire.model.grimoire;


import com.grimoire.dto.engineRule.RuleResponseDto;
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

    @Column(name = "TITULO")
    private String title;

    @Column(name = "DESCRICAO")
    private String description;

    public RuleResponseDto toDto() {
        return RuleResponseDto.builder()
                .id(this.id)
                .title(this.title)
                .idSys(this.engine.getId())
                .description(this.description)
                .build();
    }
}
