package com.grimoire.repository;


import com.grimoire.model.grimoire.CharacterSheetTabModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface CharacterSheetTabRepository extends JpaRepository<CharacterSheetTabModel, Long> {

    @Query(value = """
            SELECT taf.*
            FROM templates_aba_ficha taf
            JOIN SISTEMAS s ON taf.ID_SISTEMA = s.ID
            WHERE taf.id_sistema = :engineId
            AND s.ID_CRIADOR = :userId
            """,
            nativeQuery = true)
    Collection<CharacterSheetTabModel> findAllFiltered(
            @Param("engineId") Long engineId,
            @Param("userId") Long userId);
}
