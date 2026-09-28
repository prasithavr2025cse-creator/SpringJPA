package com.uam.hub.controller;

import com.uam.hub.entity.DockingTransaction;
import com.uam.hub.entity.LandingPad;
import com.uam.hub.service.DockingTransactionService;
import com.uam.hub.service.LandingPadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/uam")
@CrossOrigin(origins = "*")
public class UamAliasController {

    private final LandingPadService landingPadService;
    private final DockingTransactionService dockingTransactionService;

    public UamAliasController(
            LandingPadService landingPadService,
            DockingTransactionService dockingTransactionService) {
        this.landingPadService = landingPadService;
        this.dockingTransactionService = dockingTransactionService;
    }

    // Alias for Pads
    @GetMapping("/pads")
    public ResponseEntity<List<LandingPad>> getAllPads() {
        return ResponseEntity.ok(landingPadService.getAllLandingPads());
    }

    @PostMapping("/pads")
    public ResponseEntity<LandingPad> createPad(@RequestBody LandingPad pad) {
        LandingPad created = landingPadService.createLandingPad(pad);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Alias for Docking Transactions
    @GetMapping("/dockings")
    public ResponseEntity<List<DockingTransaction>> getAllDockings() {
        return ResponseEntity.ok(dockingTransactionService.getAllTransactions());
    }

    @PostMapping("/dockings")
    public ResponseEntity<DockingTransaction> createDocking(@RequestBody DockingTransaction transaction) {
        DockingTransaction created = dockingTransactionService.createTransaction(transaction);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}
