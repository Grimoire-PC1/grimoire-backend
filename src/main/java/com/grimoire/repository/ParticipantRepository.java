package com.grimoire.repository;

import com.grimoire.model.grimoire.embeddedId.ParticipantModelId;
import com.grimoire.model.grimoire.ParticipantModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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

    @Query(value = """
        SELECT EXISTS(
            SELECT p.* FROM PARTICIPANTES p
            WHERE p.ID_CAMPANHA = :idCampaign
            AND p.ID_USUARIO = :idUser
    )
    """,
            nativeQuery = true)
    Boolean exists(
            @Param("idUser") Long idUser,
            @Param("idCampaign") Long idCampaign
    );

    @Modifying
    @Transactional
    @Query(value = """
        CALL remover_dados_participante(:idUser, :idCampaign);
    """, nativeQuery = true)
    void removeParticipantData(
            @Param("idUser") Long idUser,
            @Param("idCampaign") Long idCampaign
    );
}
