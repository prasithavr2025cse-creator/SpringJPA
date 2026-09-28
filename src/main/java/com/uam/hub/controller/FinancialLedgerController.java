package com.uam.hub.controller;

import com.uam.hub.entity.FinancialLedger;
import com.uam.hub.service.FinancialLedgerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ledger")
@CrossOrigin(origins = "*")
public class FinancialLedgerController {

    private final FinancialLedgerService ledgerService;

    public FinancialLedgerController(FinancialLedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @GetMapping
    public ResponseEntity<List<FinancialLedger>> getAllLedgerEntries() {
        return ResponseEntity.ok(ledgerService.getAllLedgerEntries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FinancialLedger> getLedgerEntryById(@PathVariable Long id) {
        return ResponseEntity.ok(ledgerService.getLedgerEntryById(id));
    }

    @PostMapping
    public ResponseEntity<FinancialLedger> createLedgerEntry(@RequestBody FinancialLedger entry) {
        FinancialLedger created = ledgerService.createLedgerEntry(entry);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FinancialLedger> updateLedgerEntry(@PathVariable Long id, @RequestBody FinancialLedger details) {
        FinancialLedger updated = ledgerService.updateLedgerEntry(id, details);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLedgerEntry(@PathVariable Long id) {
        ledgerService.deleteLedgerEntry(id);
        return ResponseEntity.noContent().build();
    }
}
