package com.swp.ChildrenVaccine.repository;

import com.swp.ChildrenVaccine.entities.Appointment;
import com.swp.ChildrenVaccine.entities.VaccinationRelation;
import com.swp.ChildrenVaccine.entities.VacinePackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VaccinationRelationRepository extends JpaRepository<VaccinationRelation, String> {

    @Query("SELECT vr FROM VaccinationRelation vr WHERE vr.aPackage.packageId = :packageId")
    List<VaccinationRelation> findByPackageId(@Param("packageId") String packageId);

    @Query("SELECT vr FROM VaccinationRelation vr WHERE vr.vaccine.vaccineId = :vaccineId")
    List<VaccinationRelation> findByVaccineId(@Param("vaccineId") String vaccineId);

    Optional<VaccinationRelation> findTopByOrderByRelationIdDesc();
}