package com.grimoire.repository;

import com.grimoire.model.grimoire.PlaylistMusicModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface PlaylistMusicRepository extends JpaRepository<PlaylistMusicModel, Long> {

    @Query(value = """
            SELECT m.*
            FROM MUSICAS_PLAYLIST m
            JOIN PLAYLISTS p ON m.ID_PLAYLIST = p.ID
            WHERE (p.ID_CAMPANHA = :idCampaign)
            AND (:playlistId IS NULL OR p.ID = :playlistId)
            """,
            nativeQuery = true)
    Collection<PlaylistMusicModel> findAllByCampaign(
            @Param("idCampaign") Long idCampaign,
            @Param("playlistId") Long playlistId);
}
