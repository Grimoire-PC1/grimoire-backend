package com.grimoire.repository;

import com.grimoire.model.grimoire.CharacterModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface CharacterRepository extends JpaRepository<CharacterModel, Long> {
}
