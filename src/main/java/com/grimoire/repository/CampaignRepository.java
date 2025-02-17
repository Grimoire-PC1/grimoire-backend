package com.grimoire.repository;

import com.grimoire.model.grimoire.CampaignModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CampaignRepository extends JpaRepository<CampaignModel, String> {

    Optional<CampaignModel> findByTitle(String title);
    boolean existsByTitle(String campaignTitle);
}
