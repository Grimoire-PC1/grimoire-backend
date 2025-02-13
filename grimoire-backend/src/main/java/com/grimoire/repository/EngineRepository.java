package com.grimoire.repository;

import com.grimoire.model.grimoire.EngineModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EngineRepository extends JpaRepository<EngineModel, String> {

    Optional<EngineModel> findByName(String name);
    Optional<EngineModel> findById(long idSys);
    boolean existsByName(String name);
    boolean isPublic(String name);
}
