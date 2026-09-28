package com.uam.hub.repository;

import com.uam.hub.entity.AirspaceZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AirspaceZoneRepository extends JpaRepository<AirspaceZone, Long> {
    Optional<AirspaceZone> findByZoneCode(String zoneCode);
    List<AirspaceZone> findByIsActive(Boolean isActive);
}
