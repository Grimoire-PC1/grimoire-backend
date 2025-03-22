package com.grimoire.controller;

import com.grimoire.controller.documentation.ItemController;
import com.grimoire.dto.item.ItemCreateRequestDto;
import com.grimoire.dto.item.ItemPostRequestDto;
import com.grimoire.dto.item.ItemResponseDto;
import com.grimoire.service.interfaces.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/item")
@Validated
@CrossOrigin
@Slf4j
public class ItemControllerImpl implements ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemControllerImpl(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<ItemResponseDto> create(
            @RequestParam(name = "id_campanha") Long campaignId,
            @Validated @RequestBody ItemCreateRequestDto itemDto,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                itemService.create(campaignId, itemDto, authentication.getName()));
    }

    @Override
    @PutMapping("/update")
    public ResponseEntity<ItemResponseDto> update(
            @RequestParam(name = "id_item") Long itemId,
            @Validated @RequestBody ItemPostRequestDto itemDto,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(
                itemService.update(itemId, itemDto, authentication.getName()));
    }

    @Override
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(
            @RequestParam(name = "id_item") Long itemId,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(
                itemService.delete(itemId, authentication.getName()));
    }

    @Override
    @GetMapping("/get")
    public ResponseEntity<Collection<ItemResponseDto>> getByCampaign(
            @RequestParam(name = "id_campanha") Long campaignId,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(
                itemService.getByCampaign(campaignId, authentication.getName()));
    }
}
