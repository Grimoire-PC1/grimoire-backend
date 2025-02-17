package com.grimoire.repository;

import com.grimoire.model.grimoire.CampaignModel;
import com.grimoire.model.grimoire.EngineModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CampaignRepository extends JpaRepository<CampaignModel, Long> {

    @Query(value = """
            SELECT c.*
            FROM CAMPANHAS c
            WHERE (:idCampaign IS NULL OR c.ID = :idCampaign)
            AND c.ID_CRIADOR = :creatorId
            """,
            nativeQuery = true)
    Collection<CampaignModel> findAllFiltered(
            @Param("idCampaign") Long idCampaign,
            @Param("creatorId") Long creatorId);

}
