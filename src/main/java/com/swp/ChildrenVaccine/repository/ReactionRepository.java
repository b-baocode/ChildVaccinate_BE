package com.swp.ChildrenVaccine.repository;

import com.swp.ChildrenVaccine.entities.VaccinationReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactionRepository extends JpaRepository<VaccinationReaction, String> {
    List<VaccinationReaction> findByChild_ChildId(String childId);

    @Query("SELECT MAX(r.id) FROM VaccinationReaction r WHERE r.id LIKE 'REAC%'")
    String findMaxReactionId();

}