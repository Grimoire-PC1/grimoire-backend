package com.grimoire.repository;

import com.grimoire.model.grimoire.embeddedId.ParticipantModelId;
import com.grimoire.model.grimoire.ParticipantModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<ParticipantModel, ParticipantModelId> {

    @Query(value = """
            SELECT p.*
            FROM PARTICIPANTES p
            WHERE (p.ID_Campanha = :idCampaign)
            """,
            nativeQuery = true)
    List<ParticipantModel> findAllByCampaign(
            @Param("idCampaign") Long idCampaign
    );
}
