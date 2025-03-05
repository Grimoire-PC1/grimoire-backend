package com.grimoire.repository;

import com.grimoire.model.grimoire.CharacterModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface CharacterRepository extends JpaRepository<CharacterModel, Long> {

    @Query(value = """
            SELECT p.*
            FROM PERSONAGENS p
            WHERE (:idUser IS NULL OR p.ID_USUARIO = :idUser)
            AND (:idCampaign IS NULL OR p.ID_CAMPANHA = :idCampaign)
            """,
            nativeQuery = true)
    Collection<CharacterModel> findAllFiltered(
            @Param("idUser") Long idUser,
            @Param("idCampaign") Long idCampaign);
}
