package com.grimoire.repository;

import com.grimoire.model.grimoire.PlaylistModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface PlaylistRepository extends JpaRepository<PlaylistModel, Long> {

    @Query(value = """
            SELECT p.*
            FROM PLAYLISTS p
            WHERE (p.ID_CAMPANHA = :idCampaign)
            """,
            nativeQuery = true)
    Collection<PlaylistModel> findAllByCampaign(
            @Param("idCampaign") Long idCampaign);
}
