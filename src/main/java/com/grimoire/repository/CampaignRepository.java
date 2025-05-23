package com.grimoire.repository;

import com.grimoire.model.grimoire.CampaignModel;
import com.grimoire.model.grimoire.EngineModel;
import com.grimoire.model.grimoire.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface CampaignRepository extends JpaRepository<CampaignModel, Long> {

    Optional<CampaignModel> findByTitle(String username);
    boolean existsByTitle(String username);

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

    @Query(value = """
            SELECT c.*
            FROM CAMPANHAS c
            JOIN PARTICIPANTES p ON p.ID_CAMPANHA = c.ID
            WHERE p.ID_USUARIO = :idUser
            """,
            nativeQuery = true)
    Collection<CampaignModel> findAllParticipating(
            @Param("idUser") Long idUser);
}
