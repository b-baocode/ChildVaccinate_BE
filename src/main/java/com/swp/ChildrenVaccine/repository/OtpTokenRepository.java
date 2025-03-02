package com.swp.ChildrenVaccine.repository;

import com.swp.ChildrenVaccine.entities.OtpToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpTokenRepository extends JpaRepository<OtpToken, Long> {
    Optional<OtpToken> findByEmail(String email);
    @Transactional
    void deleteByEmail(String email);
}
