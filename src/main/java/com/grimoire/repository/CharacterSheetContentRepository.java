package com.grimoire.repository;

import com.grimoire.model.grimoire.CharacterSheetContentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CharacterSheetContentRepository extends JpaRepository<CharacterSheetContentModel, Long> {

    @Query(value = """
            SELECT c.*
            FROM sub_aba_ficha_conteudo c
            JOIN public.templates_sub_aba_ficha saf on c.id_template_sub_aba_ficha = saf.id
            WHERE :characterId = c.id_personagem
            AND (:characterSheetTabId IS NULL OR saf.id_aba_ficha = :characterSheetTabId)
            AND (:characterSheetSubTabId IS NULL OR c.id_template_sub_aba_ficha = :characterSheetSubTabId)
            """,
            nativeQuery = true)
    Collection<CharacterSheetContentModel> findAllFiltered(
            @Param("characterId") Long characterId,
            @Param("characterSheetTabId") Long characterSheetTabId,
            @Param("characterSheetSubTabId") Long characterSheetSubTabId);
}
