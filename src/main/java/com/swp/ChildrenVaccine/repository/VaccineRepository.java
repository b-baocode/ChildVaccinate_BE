package com.swp.ChildrenVaccine.repository;

import com.swp.ChildrenVaccine.entities.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, Long>{
    List<Vaccine> findAll();
    Optional<Vaccine> findByVaccineId(String id);
}
