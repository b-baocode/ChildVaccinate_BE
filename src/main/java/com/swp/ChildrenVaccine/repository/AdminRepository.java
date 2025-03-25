package com.swp.ChildrenVaccine.repository;

import com.swp.ChildrenVaccine.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
    Optional<Admin> findByUserEmail(String email);
}
