package com.swp.ChildrenVaccine.repository;

import com.swp.ChildrenVaccine.entities.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChildRepository extends JpaRepository<Child, String> {
    List<Child> findByCusId(String cusId);
    boolean existsByIdAndCusId(String childId, String cusId);
}