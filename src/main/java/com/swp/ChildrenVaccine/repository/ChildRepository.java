package com.swp.ChildrenVaccine.repository;

import com.swp.ChildrenVaccine.entities.Child;
import com.swp.ChildrenVaccine.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ChildRepository extends JpaRepository<Child, String> {
    List<Child> findByCusId_CusId(String customerId); // Tự động query theo ID của Customer
    Optional<Child> findTopByOrderByChildId();
    List<Child> findByCusId(Customer customer);
    List<Child> getChildrenByCusId(Customer cusId);
}