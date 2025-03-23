package com.swp.ChildrenVaccine.repository;

import com.swp.ChildrenVaccine.entities.VaccinationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<VaccinationRecord, String> {
    @Query("SELECT r FROM VaccinationRecord r WHERE r.appointment.child.childId = :childId")
    List<VaccinationRecord> findByChildId(@Param("childId") String childId);

    @Query("SELECT MAX(r.id) FROM VaccinationRecord r WHERE r.id LIKE 'VR%'")
    String findMaxRecordId();
}