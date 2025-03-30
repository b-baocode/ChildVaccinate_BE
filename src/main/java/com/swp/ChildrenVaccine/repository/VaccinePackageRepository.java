package com.swp.ChildrenVaccine.repository;

import com.swp.ChildrenVaccine.entities.VacinePackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VaccinePackageRepository extends JpaRepository<VacinePackage, Long>{
    List<VacinePackage> findAll();
    Optional<VacinePackage> findByPackageId(String id);
}
