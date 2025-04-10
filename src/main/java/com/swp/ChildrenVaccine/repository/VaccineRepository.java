package com.swp.ChildrenVaccine.repository;

import com.swp.ChildrenVaccine.entities.VaccinationRelation;
import com.swp.ChildrenVaccine.entities.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, String>{
    List<Vaccine> findAll();
    Optional<Vaccine> findByVaccineId(String id);
    Optional<Vaccine> findTopByOrderByVaccineIdDesc();

    @Query("SELECT v FROM Vaccine v WHERE v.name LIKE %:name%")
    List<Vaccine> findByName(@Param("name") String name);

}
