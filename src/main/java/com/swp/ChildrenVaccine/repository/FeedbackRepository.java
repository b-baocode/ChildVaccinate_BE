package com.swp.ChildrenVaccine.repository;

import com.swp.ChildrenVaccine.entities.RatingFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<RatingFeedback, String> {
    Optional<RatingFeedback> findTopByOrderByIdDesc();
}