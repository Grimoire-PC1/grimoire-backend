package com.grimoire.repository;

import com.grimoire.model.grimoire.MechanicModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface MechanicRepository extends JpaRepository<MechanicModel, Long> {

    @Query(value = """
            SELECT m.*
            FROM MECANICAS m
            JOIN SISTEMAS s ON s.ID = m.ID_SISTEMA
            WHERE (:engineId IS NULL OR m.ID_SISTEMA = :engineId)
            AND s.ID_CRIADOR = :userId
            """,
            nativeQuery = true)
    List<MechanicModel> findAllFiltered(
            @Param("userId") Long userId,
            @Param("engineId") Long engineId);
}
