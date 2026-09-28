package com.uam.hub.controller;

import com.uam.hub.entity.LandingPad;
import com.uam.hub.service.LandingPadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/landing-pads")
@CrossOrigin(origins = "*")
public class LandingPadController {

    private final LandingPadService landingPadService;

    public LandingPadController(LandingPadService landingPadService) {
        this.landingPadService = landingPadService;
    }

    @GetMapping
    public ResponseEntity<List<LandingPad>> getAllLandingPads() {
        return ResponseEntity.ok(landingPadService.getAllLandingPads());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LandingPad> getLandingPadById(@PathVariable Long id) {
        return ResponseEntity.ok(landingPadService.getLandingPadById(id));
    }

    @PostMapping
    public ResponseEntity<LandingPad> createLandingPad(@RequestBody LandingPad landingPad) {
        LandingPad created = landingPadService.createLandingPad(landingPad);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LandingPad> updateLandingPad(@PathVariable Long id, @RequestBody LandingPad padDetails) {
        LandingPad updated = landingPadService.updateLandingPad(id, padDetails);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLandingPad(@PathVariable Long id) {
        landingPadService.deleteLandingPad(id);
        return ResponseEntity.noContent().build();
    }
}
