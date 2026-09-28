package com.uam.hub.controller;

import com.uam.hub.entity.DockingTransaction;
import com.uam.hub.service.DockingTransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/docking-transactions")
@CrossOrigin(origins = "*")
public class DockingTransactionController {

    private final DockingTransactionService dockingService;

    public DockingTransactionController(DockingTransactionService dockingService) {
        this.dockingService = dockingService;
    }

    @GetMapping
    public ResponseEntity<List<DockingTransaction>> getAllTransactions() {
        return ResponseEntity.ok(dockingService.getAllTransactions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DockingTransaction> getTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok(dockingService.getTransactionById(id));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<DockingTransaction>> getTransactionsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(dockingService.getTransactionsByStatus(status));
    }

    @PostMapping("/check-in")
    public ResponseEntity<DockingTransaction> checkInDrone(
            @RequestParam Long droneId,
            @RequestParam Long landingPadId) {
        DockingTransaction transaction = dockingService.checkInDrone(droneId, landingPadId);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/check-out")
    public ResponseEntity<DockingTransaction> checkOutDrone(@PathVariable Long id) {
        DockingTransaction transaction = dockingService.checkOutDrone(id);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping
    public ResponseEntity<DockingTransaction> createTransaction(@RequestBody DockingTransaction transaction) {
        DockingTransaction created = dockingService.createTransaction(transaction);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DockingTransaction> updateTransaction(
            @PathVariable Long id,
            @RequestBody DockingTransaction details) {
        DockingTransaction updated = dockingService.updateTransaction(id, details);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        dockingService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}
