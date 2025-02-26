package com.swp.ChildrenVaccine.repository;


import com.swp.ChildrenVaccine.entities.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, String> {
    List<Reaction> findByChild_ChildId(String childId);
}