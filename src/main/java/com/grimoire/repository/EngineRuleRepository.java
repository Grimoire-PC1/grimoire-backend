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

    @Query(value = """
            SELECT rs.*
            FROM REGRAS_SISTEMA rs
            JOIN SISTEMAS s ON rs.ID_SISTEMA = s.ID
            WHERE (:idRule IS NULL OR rs.ID = :idRule)
            AND rs.ID_SISTEMA = :idSys
            """,
            nativeQuery = true)
    Collection<EngineRuleModel> findAllFiltered(
            @Param("idRule") Long idRule,
            @Param("idSys") Long idSys);

    @Query(value = """
            SELECT rs.*
            FROM REGRAS_SISTEMA rs
            JOIN SISTEMAS s ON s.ID = rs.ID_SISTEMA
            JOIN CAMPANHAS c ON c.ID_SISTEMA = s.ID
            WHERE (c.ID = :campaignId)
            """,
            nativeQuery = true)
    Collection<EngineRuleModel> findByCampaign(
            @Param("campaignId") Long campaignId);
}
