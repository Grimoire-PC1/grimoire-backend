package com.grimoire.service;

import com.grimoire.dto.item.ItemCreateRequestDto;
import com.grimoire.dto.item.ItemPostRequestDto;
import com.grimoire.dto.item.ItemResponseDto;
import com.grimoire.model.grimoire.CampaignModel;
import com.grimoire.model.grimoire.ItemModel;
import com.grimoire.model.grimoire.UserModel;
import com.grimoire.repository.*;
import com.grimoire.service.interfaces.ItemService;
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
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

        CampaignModel campaignModel = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found: " + campaignId));

        if (!campaignModel.getOwner().equals(user)) {
            throw new IllegalArgumentException("You don't have permission to this Campaign");
        }

        ItemModel item = ItemModel.builder()
                .campaign(campaignModel)
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .diceQuantity(itemDto.getDiceQuantity())
                .diceType(itemDto.getDiceType())
                .diceBonus(itemDto.getDiceBonus())
                .rollDice(itemDto.getRollDice())
                .itemQuantity(itemDto.getItemQuantity())
                .build();

        return itemRepository.save(item).toDto();
    }

    @Override
    public ItemResponseDto update(Long itemId, ItemPostRequestDto itemDto, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        ItemModel item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found: " + itemId));
        if (!item.getCampaign().getOwner().equals(user)) {
            throw new IllegalArgumentException("You don't have permission to this Item");
        }

        item.setName(itemDto.getName() == null ? item.getName() : itemDto.getName());
        item.setDescription(itemDto.getDescription() == null ? item.getDescription() : itemDto.getDescription());
        item.setDiceQuantity(itemDto.getDiceQuantity() == null ? item.getDiceQuantity() : itemDto.getDiceQuantity());
        item.setDiceType(itemDto.getDiceType() == null ? item.getDiceType() : itemDto.getDiceType());
        item.setDiceBonus(itemDto.getDiceBonus() == null ? item.getDiceBonus() : itemDto.getDiceBonus());
        item.setRollDice(itemDto.getRollDice() == null ? item.getRollDice() : itemDto.getRollDice());
        item.setItemQuantity(itemDto.getItemQuantity() == null ? item.getItemQuantity() : itemDto.getItemQuantity());

        return itemRepository.save(item).toDto();
    }

    @Override
    public String delete(Long itemId, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        ItemModel item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found: " + itemId));
        if (!item.getCampaign().getOwner().equals(user)) {
            throw new IllegalArgumentException("You don't have permission to this Item");
        }
        itemRepository.delete(item);

        return "Item deleted successfully!";
    }

    @Override
    public Collection<ItemResponseDto> getByCampaign(Long campaignId, String username) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        CampaignModel campaignModel = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found: " + campaignId));
        if (!campaignModel.getOwner().equals(user) && !participantRepository.exists(user.getId(), campaignModel.getId())) {
            throw new IllegalArgumentException("You are not part of this Campaign");
        }

        Collection<ItemModel> itens = itemRepository.findAllByCampaign(campaignId);
        return itens.stream().map(ItemModel::toDto).toList();
    }
}
