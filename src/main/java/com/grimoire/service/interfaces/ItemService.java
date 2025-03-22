package com.grimoire.service.interfaces;

import com.grimoire.dto.item.ItemCreateRequestDto;
import com.grimoire.dto.item.ItemPostRequestDto;
import com.grimoire.dto.item.ItemResponseDto;

import java.util.Collection;

public interface ItemService {
    ItemResponseDto create(Long campaignId, ItemCreateRequestDto itemDto, String username);

    ItemResponseDto update(Long itemId, ItemPostRequestDto itemDto, String username);

    String delete(Long itemId, String username);

    Collection<ItemResponseDto> getByCampaign(Long campaignId, String username);
}
