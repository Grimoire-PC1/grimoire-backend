package com.grimoire.repository;

import com.grimoire.model.grimoire.CampaignModel;
import com.grimoire.model.grimoire.SessionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface SessionRepository extends JpaRepository<SessionModel, Long> {

    @Query(value = """
            SELECT s.*
            FROM SESSOES s
            WHERE (:idCampaign IS NULL OR s.ID_CAMPANHA = :idCampaign)
            """,
            nativeQuery = true)
    Collection<SessionModel> findAllByCampaign(
            @Param("idCampaign") Long idCampaign);
}
