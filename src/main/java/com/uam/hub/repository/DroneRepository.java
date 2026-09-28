package com.uam.hub.repository;

import com.uam.hub.entity.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Long> {
    Optional<Drone> findByRegistrationNumber(String registrationNumber);
    List<Drone> findByStatus(String status);
}
