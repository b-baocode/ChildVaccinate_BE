package com.swp.ChildrenVaccine.repository;

import com.swp.ChildrenVaccine.entities.Child;
import com.swp.ChildrenVaccine.entities.Customer;
import com.swp.ChildrenVaccine.entities.Schedule;
import com.swp.ChildrenVaccine.enums.ScheduleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ChildRepository extends JpaRepository<Child, String> {
    List<Child> findAll();
    List<Child> getChildrenByCusId(Customer cusId);
    Optional<Child> findTopByOrderByChildIdDesc();

}