package com.grimoire.controller.documentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.grimoire.dto.playlist.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

@Tag(name = "Playlist", description = "Serviço de Playlist de Campanha")
public interface PlaylistController {

    @Operation(description = "Registrar Playlist", summary = "Registrar nova Playlist de Campanha de RPG no Grimoire")
    ResponseEntity<PlaylistResponseDto> createPlaylist(
            @Parameter(
                    name = "id_campanha",
                    description = "ID da Campanha de RPG.",
                    required = true
            )
            Long campaignId,
            @Validated @RequestBody PlaylistCreateRequestDto playlistDto,
            Authentication authentication) throws JsonProcessingException;

    @Operation(description = "Atualizar Playlist", summary = "Atualizar Playlist no Grimoire")
    ResponseEntity<PlaylistResponseDto> updatePlaylist(
            @Parameter(
                    name = "id_playlist",
                    description = "ID da Playlist.",
                    required = true
            )
            Long playlistId,
            @Validated @RequestBody PlaylistPostRequestDto playlistDto,
            Authentication authentication) throws JsonProcessingException;

    @Operation(description = "Remover Playlist", summary = "Remover Playlist no Grimoire.")
    ResponseEntity<String> deletePlaylist(
            @Parameter(
                    name = "id_playlist",
                    description = "ID da Playlist.",
                    required = true
            )
            Long playlistId,
            Authentication authentication);

    @Operation(description = "Pegar Playlists", summary = "Pegar Playlists no Grimoire.")
    ResponseEntity<Collection<PlaylistResponseDto>> getPlaylist(
            @Parameter(
                    name = "id_campanha",
                    description = "ID da Campanha de RPG.",
                    required = true
            )
            Long campaignId,
            Authentication authentication);

    @Operation(description = "Registrar Música em Playlist", summary = "Registrar nova Música em Playlist no Grimoire")
    ResponseEntity<PlaylistMusicResponseDto> createPlaylistMusic(
            @Parameter(
                    name = "id_playlist",
                    description = "ID da Playlist.",
                    required = true
            )
            Long playlistId,
            @Validated @RequestBody PlaylistMusicCreateRequestDto musicDto,
            Authentication authentication) throws JsonProcessingException;

    @Operation(description = "Atualizar Música em Playlist", summary = "Atualizar Música em Playlist no Grimoire")
    ResponseEntity<PlaylistMusicResponseDto> updatePlaylistMusic(
            @Parameter(
                    name = "id_musica",
                    description = "ID da Música.",
                    required = true
            )
            Long musicId,
            @Validated @RequestBody PlaylistMusicPostRequestDto musicDto,
            Authentication authentication) throws JsonProcessingException;

    @Operation(description = "Remover Música em Playlist", summary = "Remover Música em Playlist no Grimoire.")
    ResponseEntity<String> deletePlaylistMusic(
            @Parameter(
                    name = "id_musica",
                    description = "ID da Música em Playlist.",
                    required = true
            )
            Long musicId,
            Authentication authentication);

    @Operation(description = "Pegar Músicas em Playlist", summary = "Pegar informações de Músicas em Playlist no Grimoire.")
    ResponseEntity<Collection<PlaylistMusicResponseDto>> getPlaylistMusic(
            @Parameter(
                    name = "id_campanha",
                    description = "ID da Campanha de RPG.",
                    required = true
            )
            Long campaignId,
            @Parameter(
                    name = "id_playlist",
                    description = "ID da Playlist.",
                    required = false
            )
            Long playlistId,
            Authentication authentication);

}
