package com.uam.hub.service;

import com.uam.hub.entity.DockingTransaction;
import com.uam.hub.entity.Drone;
import com.uam.hub.entity.LandingPad;
import com.uam.hub.exception.ResourceNotFoundException;
import com.uam.hub.repository.DockingTransactionRepository;
import com.uam.hub.repository.DroneRepository;
import com.uam.hub.repository.LandingPadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class DockingTransactionService {

    private final DockingTransactionRepository dockingRepository;
    private final DroneRepository droneRepository;
    private final LandingPadRepository landingPadRepository;

    public DockingTransactionService(
            DockingTransactionRepository dockingRepository,
            DroneRepository droneRepository,
            LandingPadRepository landingPadRepository) {
        this.dockingRepository = dockingRepository;
        this.droneRepository = droneRepository;
        this.landingPadRepository = landingPadRepository;
    }

    public List<DockingTransaction> getAllTransactions() {
        return dockingRepository.findAll();
    }

    public DockingTransaction getTransactionById(Long id) {
        return dockingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DockingTransaction", "id", id));
    }

    public List<DockingTransaction> getTransactionsByStatus(String status) {
        return dockingRepository.findByStatus(status);
    }

    public DockingTransaction checkInDrone(Long droneId, Long landingPadId) {
        Drone drone = droneRepository.findById(droneId)
                .orElseThrow(() -> new ResourceNotFoundException("Drone", "id", droneId));
        LandingPad pad = landingPadRepository.findById(landingPadId)
                .orElseThrow(() -> new ResourceNotFoundException("LandingPad", "id", landingPadId));

        drone.setStatus("DOCKED");
        pad.setStatus("OCCUPIED");

        droneRepository.save(drone);
        landingPadRepository.save(pad);

        DockingTransaction transaction = new DockingTransaction();
        transaction.setDrone(drone);
        transaction.setLandingPad(pad);
        transaction.setStartTime(LocalDateTime.now());
        transaction.setStatus("ACTIVE");
        transaction.setFeeAmount(50.0); // Base check-in fee

        return dockingRepository.save(transaction);
    }

    public DockingTransaction checkOutDrone(Long transactionId) {
        DockingTransaction transaction = getTransactionById(transactionId);
        LocalDateTime now = LocalDateTime.now();
        transaction.setEndTime(now);

        long hours = 1;
        if (transaction.getStartTime() != null) {
            long minutes = Duration.between(transaction.getStartTime(), now).toMinutes();
            hours = Math.max(1, (long) Math.ceil(minutes / 60.0));
        }
        
        double hourlyRate = 75.0;
        transaction.setFeeAmount(hours * hourlyRate);
        transaction.setStatus("COMPLETED");

        if (transaction.getDrone() != null) {
            Drone drone = transaction.getDrone();
            drone.setStatus("AVAILABLE");
            droneRepository.save(drone);
        }

        if (transaction.getLandingPad() != null) {
            LandingPad pad = transaction.getLandingPad();
            pad.setStatus("AVAILABLE");
            landingPadRepository.save(pad);
        }

        return dockingRepository.save(transaction);
    }

    public DockingTransaction createTransaction(DockingTransaction transaction) {
        if (transaction.getStartTime() == null) {
            transaction.setStartTime(LocalDateTime.now());
        }
        if (transaction.getStatus() == null) {
            transaction.setStatus("ACTIVE");
        }
        if (transaction.getDrone() != null && transaction.getDrone().getId() != null) {
            Drone drone = droneRepository.findById(transaction.getDrone().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Drone", "id", transaction.getDrone().getId()));
            transaction.setDrone(drone);
        }
        if (transaction.getLandingPad() != null && transaction.getLandingPad().getId() != null) {
            LandingPad pad = landingPadRepository.findById(transaction.getLandingPad().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("LandingPad", "id", transaction.getLandingPad().getId()));
            transaction.setLandingPad(pad);
        }
        return dockingRepository.save(transaction);
    }

    public DockingTransaction updateTransaction(Long id, DockingTransaction details) {
        DockingTransaction tx = getTransactionById(id);
        if (details.getStartTime() != null) tx.setStartTime(details.getStartTime());
        if (details.getEndTime() != null) tx.setEndTime(details.getEndTime());
        if (details.getStatus() != null) tx.setStatus(details.getStatus());
        if (details.getFeeAmount() != null) tx.setFeeAmount(details.getFeeAmount());
        return dockingRepository.save(tx);
    }

    public void deleteTransaction(Long id) {
        DockingTransaction tx = getTransactionById(id);
        dockingRepository.delete(tx);
    }
}
