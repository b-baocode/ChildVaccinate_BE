package com.swp.ChildrenVaccine.repository;

import com.swp.ChildrenVaccine.entities.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, Long>{
    List<Vaccine> findAll();
}
