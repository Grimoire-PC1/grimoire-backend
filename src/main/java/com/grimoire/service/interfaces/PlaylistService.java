package com.grimoire.service.interfaces;

import com.grimoire.dto.playlist.*;

import java.util.Collection;

public interface PlaylistService {
    PlaylistResponseDto createPlaylist(Long campaignId, PlaylistCreateRequestDto playlistDto, String name);

    PlaylistResponseDto editPlaylist(Long playlistId, PlaylistPostRequestDto playlistDto, String name);

    String deletePlaylist(Long playlistId, String name);

    Collection<PlaylistResponseDto> getPlaylist(Long campaignId, String name);

    PlaylistMusicResponseDto createPlaylistMusic(Long playlistId, PlaylistMusicCreateRequestDto musicDto, String name);

    PlaylistMusicResponseDto editPlaylistMusic(Long musicId, PlaylistMusicPostRequestDto musicDto, String name);

    String deletePlaylistMusic(Long musicId, String name);

    Collection<PlaylistMusicResponseDto> getPlaylistMusic(Long campaignId, Long playlistId, String name);
}
