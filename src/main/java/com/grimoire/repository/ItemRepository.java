package com.grimoire.repository;

import com.grimoire.model.grimoire.ItemModel;
import com.grimoire.model.grimoire.SessionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ItemRepository extends JpaRepository<ItemModel, Long> {
    @Query(value = """
            SELECT s.*
            FROM ITENS s
            WHERE (:idCampaign IS NULL OR s.ID_CAMPANHA = :idCampaign)
            """,
            nativeQuery = true)
    Collection<ItemModel> findAllByCampaign(
            @Param("idCampaign") Long idCampaign);
}
