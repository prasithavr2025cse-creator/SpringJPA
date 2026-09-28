package com.uam.hub.repository;

import com.uam.hub.entity.DroneOperator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DroneOperatorRepository extends JpaRepository<DroneOperator, Long> {
    Optional<DroneOperator> findByLicenseNumber(String licenseNumber);
}
