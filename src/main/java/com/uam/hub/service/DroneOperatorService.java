package com.uam.hub.service;

import com.uam.hub.entity.DroneOperator;
import com.uam.hub.exception.ResourceNotFoundException;
import com.uam.hub.repository.DroneOperatorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DroneOperatorService {

    private final DroneOperatorRepository operatorRepository;

    public DroneOperatorService(DroneOperatorRepository operatorRepository) {
        this.operatorRepository = operatorRepository;
    }

    public List<DroneOperator> getAllOperators() {
        return operatorRepository.findAll();
    }

    public DroneOperator getOperatorById(Long id) {
        return operatorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DroneOperator", "id", id));
    }

    @Transactional
    public DroneOperator createOperator(DroneOperator operator) {
        return operatorRepository.save(operator);
    }

    @Transactional
    public DroneOperator updateOperator(Long id, DroneOperator operatorDetails) {
        DroneOperator operator = getOperatorById(id);
        if (operatorDetails.getName() != null) {
            operator.setName(operatorDetails.getName());
        }
        if (operatorDetails.getLicenseNumber() != null) {
            operator.setLicenseNumber(operatorDetails.getLicenseNumber());
        }
        if (operatorDetails.getContactEmail() != null) {
            operator.setContactEmail(operatorDetails.getContactEmail());
        }
        if (operatorDetails.getContactPhone() != null) {
            operator.setContactPhone(operatorDetails.getContactPhone());
        }
        if (operatorDetails.getOperationalStatus() != null) {
            operator.setOperationalStatus(operatorDetails.getOperationalStatus());
        }
        return operatorRepository.save(operator);
    }

    @Transactional
    public void deleteOperator(Long id) {
        DroneOperator operator = getOperatorById(id);
        operatorRepository.delete(operator);
    }
}
