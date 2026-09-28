package com.uam.hub.controller;

import com.uam.hub.entity.AirspaceZone;
import com.uam.hub.service.AirspaceZoneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airspace-zones")
@CrossOrigin(origins = "*")
public class AirspaceZoneController {

    private final AirspaceZoneService zoneService;

    public AirspaceZoneController(AirspaceZoneService zoneService) {
        this.zoneService = zoneService;
    }

    @GetMapping
    public ResponseEntity<List<AirspaceZone>> getAllZones() {
        return ResponseEntity.ok(zoneService.getAllZones());
    }

    @GetMapping("/active")
    public ResponseEntity<List<AirspaceZone>> getActiveZones() {
        return ResponseEntity.ok(zoneService.getActiveZones());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirspaceZone> getZoneById(@PathVariable Long id) {
        return ResponseEntity.ok(zoneService.getZoneById(id));
    }

    @PostMapping
    public ResponseEntity<AirspaceZone> createZone(@RequestBody AirspaceZone zone) {
        AirspaceZone created = zoneService.createZone(zone);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AirspaceZone> updateZone(@PathVariable Long id, @RequestBody AirspaceZone zoneDetails) {
        AirspaceZone updated = zoneService.updateZone(id, zoneDetails);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteZone(@PathVariable Long id) {
        zoneService.deleteZone(id);
        return ResponseEntity.noContent().build();
    }
}
