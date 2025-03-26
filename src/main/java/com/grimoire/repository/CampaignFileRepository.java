package com.grimoire.repository;

import com.grimoire.model.grimoire.CampaignFileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CampaignFileRepository extends JpaRepository<CampaignFileModel, Long>  {
    @Query(value = """
            SELECT a.*
            FROM ARQUIVOS a
            JOIN PACOTES p ON p.ID = a.ID_PACOTE
            JOIN CAMPANHAS c ON c.ID = p.ID_CAMPANHA
            WHERE p.ID_CAMPANHA = :campaignId
            AND (:parentPackageId IS NULL OR p.ID_PACOTE_PAI = :parentPackageId)
            AND (c.ID_CRIADOR = :userId
            OR p.ID_USUARIO = :userId
            OR p.PUBLICA = 'true')
            """,
            nativeQuery = true)
    Collection<CampaignFileModel> findAllFiltered(
            @Param("campaignId") Long campaignId,
            @Param("parentPackageId") Long parentPackageId,
            @Param("userId") Long userId);
}
