package com.swp.ChildrenVaccine.repository;

import com.swp.ChildrenVaccine.entities.VaccinationRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccinationRelationRepository extends JpaRepository<VaccinationRelation, String> {

    @Query("SELECT vr FROM VaccinationRelation vr WHERE vr.aPackage.packageId = :packageId")
    List<VaccinationRelation> findByPackageId(@Param("packageId") String packageId);
}