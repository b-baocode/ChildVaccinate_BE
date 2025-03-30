package com.swp.ChildrenVaccine.repository;

import com.swp.ChildrenVaccine.entities.RatingFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface FeedbackRepository extends JpaRepository<RatingFeedback, String> {
    Optional<RatingFeedback> findTopByOrderByIdDesc();
    @Query("SELECT f FROM RatingFeedback f WHERE f.appointment.appId = :appId")
    List<RatingFeedback> findByAppId(@Param("appId") String appId);


    @Query("SELECT f FROM RatingFeedback f WHERE f.customer.cusId = :cusId")
    List<RatingFeedback> findByCusId(@Param("cusId") String cusId);
}
