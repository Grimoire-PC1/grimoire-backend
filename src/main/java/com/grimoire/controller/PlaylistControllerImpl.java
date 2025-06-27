package com.grimoire.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.grimoire.controller.documentation.PlaylistController;
import com.grimoire.dto.playlist.*;
import com.grimoire.service.interfaces.PlaylistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/playlist")
@Validated
@CrossOrigin
@Slf4j
public class PlaylistControllerImpl implements PlaylistController {
    PlaylistService playlistService;

    @Autowired
    public PlaylistControllerImpl(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<PlaylistResponseDto> createPlaylist(
            @RequestParam(name = "id_campanha") Long campaignId,
            @Validated @RequestBody PlaylistCreateRequestDto playlistDto,
            Authentication authentication) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                playlistService.createPlaylist(
                        campaignId, playlistDto, authentication.getName()));
    }

    @Override
    @PutMapping("/update")
    public ResponseEntity<PlaylistResponseDto> updatePlaylist(
            @RequestParam(name = "id_playlist") Long playlistId,
            @Validated @RequestBody PlaylistPostRequestDto playlistDto,
            Authentication authentication) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).body(
                playlistService.editPlaylist(
                        playlistId, playlistDto, authentication.getName()));
    }

    @Override
    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePlaylist(
            @RequestParam(name = "id_playlist") Long playlistId,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(
                playlistService.deletePlaylist(
                        playlistId, authentication.getName()));
    }

    @Override
    @GetMapping("/get")
    public ResponseEntity<Collection<PlaylistResponseDto>> getPlaylist(
            @RequestParam(name = "id_campanha") Long campaignId,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(
                playlistService.getPlaylist(
                        campaignId, authentication.getName()));
    }

    @Override
    @PostMapping("/music/register")
    public ResponseEntity<PlaylistMusicResponseDto> createPlaylistMusic(
            @RequestParam(name = "id_playlist") Long playlistId,
            @Validated @RequestBody PlaylistMusicCreateRequestDto musicDto,
            Authentication authentication) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                playlistService.createPlaylistMusic(
                        playlistId, musicDto, authentication.getName()));
    }

    @Override
    @PutMapping("/music/update")
    public ResponseEntity<PlaylistMusicResponseDto> updatePlaylistMusic(
            @RequestParam(name = "id_musica") Long musicId,
            @Validated @RequestBody PlaylistMusicPostRequestDto musicDto,
            Authentication authentication) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).body(
                playlistService.editPlaylistMusic(
                        musicId, musicDto, authentication.getName()));
    }

    @Override
    @DeleteMapping("/music/delete")
    public ResponseEntity<String> deletePlaylistMusic(
            @RequestParam(name = "id_musica") Long musicId,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(
                playlistService.deletePlaylistMusic(
                        musicId, authentication.getName()));
    }

    @Override
    @GetMapping("/music/get")
    public ResponseEntity<Collection<PlaylistMusicResponseDto>> getPlaylistMusic(
            @RequestParam(name = "id_campanha") Long campaignId,
            @RequestParam(name = "id_playlist", required = false) Long playlistId,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(
                playlistService.getPlaylistMusic(
                        campaignId, playlistId, authentication.getName()));
    }
}
