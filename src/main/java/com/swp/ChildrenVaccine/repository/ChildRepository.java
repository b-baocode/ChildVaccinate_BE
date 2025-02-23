package com.swp.ChildrenVaccine.repository;

import com.swp.ChildrenVaccine.entities.Child;
import com.swp.ChildrenVaccine.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ChildRepository extends JpaRepository<Child, String> {
    List<Child> getChildrenByCusId(String cusId);
    boolean existsByChildIdAndCusId(String childId, String cusId);
    Optional<Child> findTopByOrderByChildId();
}