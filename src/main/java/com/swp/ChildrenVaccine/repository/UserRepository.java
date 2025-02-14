package com.swp.ChildrenVaccine.repository;

import com.swp.ChildrenVaccine.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUserID(String id);
    boolean existsByEmail(String email);

}
