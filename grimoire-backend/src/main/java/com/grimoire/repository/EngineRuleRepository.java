package com.grimoire.repository;

import com.grimoire.model.grimoire.EngineRuleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EngineRuleRepository extends JpaRepository<EngineRuleModel, Long> {

    Optional<EngineRuleModel> findByTitle(String name);
    Optional<EngineRuleModel> findById(Long idRule);
    boolean existsByTitle(String name);
}
