package com.swp.ChildrenVaccine.repository;

import com.swp.ChildrenVaccine.entities.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(String id);
    boolean existsByEmail(String email);
    Optional<User> findTopByOrderByIdDesc();
    String email(@Size(max = 100) @NotNull String email);
}
