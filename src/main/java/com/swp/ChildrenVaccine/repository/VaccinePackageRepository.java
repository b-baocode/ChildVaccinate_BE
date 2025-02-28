package com.swp.ChildrenVaccine.repository;

import com.swp.ChildrenVaccine.entities.VacinePackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccinePackageRepository extends JpaRepository<VacinePackage, String> {
}
