package com.grimoire.repository;

import com.grimoire.model.grimoire.EngineModel;
import com.grimoire.model.grimoire.EngineRuleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface EngineRuleRepository extends JpaRepository<EngineRuleModel, Long> {
    Optional<EngineRuleModel> findById(Long idRule);

    @Query(value = """
            SELECT rs.*
            FROM REGRAS_SISTEMA rs
            JOIN SISTEMAS s ON rs.ID_SISTEMA = s.ID 
            WHERE (:idRule IS NULL OR rs.ID = :idRule)
            AND (:idSys IS NULL OR rs.ID_SISTEMA = :idSys)
            AND s.ID_CRIADOR = :creatorId
            """,
            nativeQuery = true)
    Collection<EngineRuleModel> findAllFiltered(
            @Param("idRule") Long idRule,
            @Param("idSys") Long idSys,
            @Param("creatorId") Long creatorId);
}
