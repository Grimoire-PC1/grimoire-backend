package com.grimoire.service;

import com.grimoire.dto.item.ItemCreateRequestDto;
import com.grimoire.dto.item.ItemPostRequestDto;
import com.grimoire.dto.item.ItemResponseDto;
import com.grimoire.repository.*;
import com.grimoire.service.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final CampaignRepository campaignRepository;
    private final ParticipantRepository participantRepository;
    private final UserRepository userRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, CampaignRepository campaignRepository, ParticipantRepository participantRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.campaignRepository = campaignRepository;
        this.participantRepository = participantRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ItemResponseDto create(Long campaignId, ItemCreateRequestDto itemDto, String username) {
        return null;
    }

    @Override
    public ItemResponseDto update(Long itemId, ItemPostRequestDto itemDto, String username) {
        return null;
    }

    @Override
    public String delete(Long itemId, String username) {
        return null;
    }

    @Override
    public Collection<ItemResponseDto> getByCampaign(Long campaignId, String username) {
        return null;
    }
}
