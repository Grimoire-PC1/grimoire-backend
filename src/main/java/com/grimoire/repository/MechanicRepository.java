package com.grimoire.repository;

import com.grimoire.dto.mechanic.MechanicResponseDto;
import com.grimoire.model.grimoire.MechanicModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface MechanicRepository extends JpaRepository<MechanicModel, Long> {

    @Query(value = """
            SELECT m.*
            FROM MECANICAS m
            JOIN SISTEMAS s ON s.ID = m.ID_SISTEMA
            WHERE (m.ID_SISTEMA = :engineId)
            """,
            nativeQuery = true)
    List<MechanicModel> findAllFiltered(
            @Param("engineId") Long engineId);

    @Query(value = """
            SELECT m.*
            FROM MECANICAS m
            JOIN SISTEMAS s ON s.ID = m.ID_SISTEMA
            JOIN CAMPANHAS c ON c.ID_SISTEMA = s.ID
            WHERE (c.ID = :campaignId)
            """,
            nativeQuery = true)
    Collection<MechanicModel> findByCampaign(
            @Param("campaignId") Long campaignId);
}
