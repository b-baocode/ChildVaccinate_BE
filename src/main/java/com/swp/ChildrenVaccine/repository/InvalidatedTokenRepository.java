package com.swp.ChildrenVaccine.repository;

import com.swp.ChildrenVaccine.entities.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
}
