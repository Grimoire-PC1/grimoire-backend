package com.grimoire.repository;


import com.grimoire.model.grimoire.CampaignPackageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignPackageRepository extends JpaRepository<CampaignPackageModel, Long> {
    @Query(value = """
            SELECT p.*
            FROM PACOTES p
            JOIN CAMPANHAS c ON c.ID = p.ID_CAMPANHA
            WHERE p.ID_CAMPANHA = :campaignId
            AND (:parentPackageId IS NULL OR p.ID_PACOTE_PAI = :parentPackageId)
            AND (c.ID_CRIADOR = :userId
            OR p.ID_USUARIO = :userId
            OR p.PUBLICA = 'true')
            """,
            nativeQuery = true)
    List<CampaignPackageModel> findAllFiltered(
            @Param("campaignId") Long campaignId,
            @Param("parentPackageId") Long parentPackageId,
            @Param("userId") Long userId);
}
