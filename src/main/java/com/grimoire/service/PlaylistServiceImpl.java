package com.grimoire.service;

import com.grimoire.dto.playlist.*;
import com.grimoire.model.grimoire.*;
import com.grimoire.repository.*;
import com.grimoire.service.interfaces.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PlaylistServiceImpl implements PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final PlaylistMusicRepository musicRepository;
    private final UserRepository userRepository;
    private final CampaignRepository campaignRepository;

    @Autowired
    public PlaylistServiceImpl(PlaylistRepository playlistRepository, PlaylistMusicRepository musicRepository, UserRepository userRepository, CampaignRepository campaignRepository) {
        this.playlistRepository = playlistRepository;
        this.musicRepository = musicRepository;
        this.userRepository = userRepository;
        this.campaignRepository = campaignRepository;
    }
    @Override
    public PlaylistResponseDto createPlaylist(
            Long campaignId, PlaylistCreateRequestDto playlistDto, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

        CampaignModel campaignModel = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found: " + campaignId));

        if (!campaignModel.getOwner().equals(user)) {
            throw new IllegalArgumentException("You don't have permission to this Campaign");
        }

        PlaylistModel model = PlaylistModel.builder()
                .campaign(campaignModel)
                .name(playlistDto.getName())
                .build();

        return playlistRepository.save(model).toDto();
    }

    @Override
    public PlaylistResponseDto editPlaylist(
            Long playlistId, PlaylistPostRequestDto playlistDto, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        PlaylistModel model = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new IllegalArgumentException("Playlist not found: " + playlistId));

        if (!model.getCampaign().getOwner().equals(user)) {
            throw new IllegalArgumentException("You don't have permission to this Playlist");
        }

        model.setName(playlistDto.getName() == null ? model.getName() : playlistDto.getName());

        return playlistRepository.save(model).toDto();
    }

    @Override
    public String deletePlaylist(
            Long playlistId, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        PlaylistModel model = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new IllegalArgumentException("Playlist not found: " + playlistId));

        if (!model.getCampaign().getOwner().equals(user)) {
            throw new IllegalArgumentException("You don't have permission to this Playlist");
        }

        playlistRepository.delete(model);
        return "Playlist deleted successfully!";
    }

    @Override
    public Collection<PlaylistResponseDto> getPlaylist(
            Long campaignId, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

        CampaignModel campaignModel = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found: " + campaignId));

        if (!campaignModel.getOwner().equals(user)) {
            throw new IllegalArgumentException("You are not owner of this Campaign");
        }

        Collection<PlaylistModel> models = playlistRepository.findAllByCampaign(campaignId);
        return models.stream().map(PlaylistModel::toDto).toList();
    }

    @Override
    public PlaylistMusicResponseDto createPlaylistMusic(
            Long playlistId, PlaylistMusicCreateRequestDto musicDto, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

        PlaylistModel playlistModel = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new IllegalArgumentException("Playlist not found: " + playlistId));

        if (!playlistModel.getCampaign().getOwner().equals(user)) {
            throw new IllegalArgumentException("You don't have permission to this Playlist");
        }

        PlaylistMusicModel model = PlaylistMusicModel.builder()
                .playlist(playlistModel)
                .name(musicDto.getName())
                .link(musicDto.getLink())
                .build();

        return musicRepository.save(model).toDto();
    }

    @Override
    public PlaylistMusicResponseDto editPlaylistMusic(
            Long musicId, PlaylistMusicPostRequestDto musicDto, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        PlaylistMusicModel model = musicRepository.findById(musicId)
                .orElseThrow(() -> new IllegalArgumentException("Music not found: " + musicId));

        if (!model.getPlaylist().getCampaign().getOwner().equals(user)) {
            throw new IllegalArgumentException("You don't have permission to this Music");
        }

        model.setName(musicDto.getName() == null ? model.getName() : musicDto.getName());
        model.setLink(musicDto.getLink() == null ? model.getLink() : musicDto.getLink());

        return musicRepository.save(model).toDto();
    }

    @Override
    public String deletePlaylistMusic(
            Long musicId, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        PlaylistMusicModel model = musicRepository.findById(musicId)
                .orElseThrow(() -> new IllegalArgumentException("Music not found: " + musicId));

        if (!model.getPlaylist().getCampaign().getOwner().equals(user)) {
            throw new IllegalArgumentException("You don't have permission to this Music");
        }

        musicRepository.delete(model);
        return "Music deleted successfully!";
    }

    @Override
    public Collection<PlaylistMusicResponseDto> getPlaylistMusic(
            Long campaignId, Long playlistId, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

        CampaignModel campaignModel = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found: " + campaignId));

        if (!campaignModel.getOwner().equals(user)) {
            throw new IllegalArgumentException("You are not owner of this Campaign");
        }

        Collection<PlaylistMusicModel> models = musicRepository.findAllByCampaign(campaignId, playlistId);
        return models.stream().map(PlaylistMusicModel::toDto).toList();
    }
}
