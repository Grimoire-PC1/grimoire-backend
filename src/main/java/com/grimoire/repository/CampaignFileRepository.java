package com.grimoire.repository;

import com.grimoire.model.grimoire.CampaignFileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignFileRepository extends JpaRepository<CampaignFileModel, Long>  {
}
