package com.grimoire.repository;

import com.grimoire.model.grimoire.EngineModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface EngineRepository extends JpaRepository<EngineModel, Long> {
    Optional<EngineModel> findById(long idSys);
    Collection<EngineModel> findAllByCreator_Id(Long creatorId);
    Collection<EngineModel> findAllByEngineType_Id(Long typeId);

    @Query(value = """
            SELECT s.*
            FROM SISTEMAS s
            WHERE (:idSys IS NULL OR s.ID = :idSys)
            AND s.ID_CRIADOR = :creatorId
            """,
            nativeQuery = true)
    Collection<EngineModel> findAllFiltered(
            @Param("idSys") Long idSys,
            @Param("creatorId") Long creatorId);
}
