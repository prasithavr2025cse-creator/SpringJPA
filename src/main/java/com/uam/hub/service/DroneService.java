package com.uam.hub.service;

import com.uam.hub.entity.Drone;
import com.uam.hub.entity.DroneOperator;
import com.uam.hub.exception.ResourceNotFoundException;
import com.uam.hub.repository.DroneOperatorRepository;
import com.uam.hub.repository.DroneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DroneService {

    private final DroneRepository droneRepository;
    private final DroneOperatorRepository operatorRepository;

    public DroneService(DroneRepository droneRepository, DroneOperatorRepository operatorRepository) {
        this.droneRepository = droneRepository;
        this.operatorRepository = operatorRepository;
    }

    public List<Drone> getAllDrones() {
        return droneRepository.findAll();
    }

    public Drone getDroneById(Long id) {
        return droneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Drone", "id", id));
    }

    @Transactional
    public Drone createDrone(Drone drone) {
        if (drone.getOperator() != null && drone.getOperator().getId() != null) {
            Long operatorId = drone.getOperator().getId();
            DroneOperator operator = operatorRepository.findById(operatorId)
                    .orElseThrow(() -> new ResourceNotFoundException("DroneOperator", "id", operatorId));
            drone.setOperator(operator);
        }
        return droneRepository.save(drone);
    }

    @Transactional
    public Drone updateDrone(Long id, Drone droneDetails) {
        Drone drone = getDroneById(id);

        if (droneDetails.getModel() != null) {
            drone.setModel(droneDetails.getModel());
        }
        if (droneDetails.getRegistrationNumber() != null) {
            drone.setRegistrationNumber(droneDetails.getRegistrationNumber());
        }
        if (droneDetails.getBatteryCapacity() != null) {
            drone.setBatteryCapacity(droneDetails.getBatteryCapacity());
        }
        if (droneDetails.getCurrentBatteryLevel() != null) {
            drone.setCurrentBatteryLevel(droneDetails.getCurrentBatteryLevel());
        }
        if (droneDetails.getPayloadCapacity() != null) {
            drone.setPayloadCapacity(droneDetails.getPayloadCapacity());
        }
        if (droneDetails.getStatus() != null) {
            drone.setStatus(droneDetails.getStatus());
        }
        if (droneDetails.getOperator() != null && droneDetails.getOperator().getId() != null) {
            Long operatorId = droneDetails.getOperator().getId();
            DroneOperator operator = operatorRepository.findById(operatorId)
                    .orElseThrow(() -> new ResourceNotFoundException("DroneOperator", "id", operatorId));
            drone.setOperator(operator);
        }

        return droneRepository.save(drone);
    }

    @Transactional
    public void deleteDrone(Long id) {
        Drone drone = getDroneById(id);
        droneRepository.delete(drone);
    }
}
