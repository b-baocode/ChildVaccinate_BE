package com.swp.ChildrenVaccine.repository;

import com.swp.ChildrenVaccine.entities.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, String> {
    Optional<Staff> findByUserEmail(String email);
}
