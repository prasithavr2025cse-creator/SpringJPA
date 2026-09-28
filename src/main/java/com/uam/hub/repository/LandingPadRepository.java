package com.uam.hub.repository;

import com.uam.hub.entity.LandingPad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LandingPadRepository extends JpaRepository<LandingPad, Long> {
    Optional<LandingPad> findByPadCode(String padCode);
    List<LandingPad> findByStatus(String status);
}
