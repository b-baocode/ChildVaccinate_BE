package com.swp.ChildrenVaccine.repository;

import com.swp.ChildrenVaccine.entities.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildRepository extends JpaRepository<Child, String> {
//    List<Child> getChildrenByCusId(String cusId);
//    boolean existsByChildIdAndCusId(String childId, String cusId);

}