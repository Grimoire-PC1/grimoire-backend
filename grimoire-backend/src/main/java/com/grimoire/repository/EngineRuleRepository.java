package com.grimoire.repository;

import com.grimoire.model.joinTables.EngineRuleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EngineRuleRepository extends JpaRepository<EngineRuleModel, String> {

    Optional<EngineRuleModel> findByName(String name);
    Optional<EngineRuleModel> findById(Long idRule);
    boolean existsByName(String name);
}
