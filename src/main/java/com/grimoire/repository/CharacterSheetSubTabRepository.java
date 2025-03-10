package com.grimoire.repository;

import com.grimoire.model.grimoire.CharacterSheetSubTabModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CharacterSheetSubTabRepository extends JpaRepository<CharacterSheetSubTabModel, Long> {
    @Query(value = """
            SELECT staf.*
            FROM templates_sub_aba_ficha staf
            JOIN templates_aba_ficha taf ON staf.id_aba_ficha = taf.id
            JOIN SISTEMAS s ON taf.ID_SISTEMA = s.ID
            WHERE taf.id_sistema = :engineId
            AND s.ID_CRIADOR = :userId
            AND (:characterSheetTabId IS NULL OR staf.id_aba_ficha = :characterSheetTabId)
            """,
            nativeQuery = true)
    Collection<CharacterSheetSubTabModel> findAllFiltered(
            @Param("engineId") Long engineId,
            @Param("characterSheetTabId") Long characterSheetTabId,
            @Param("userId") Long userId);
}
