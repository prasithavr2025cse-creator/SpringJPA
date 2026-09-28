package com.uam.hub.controller;

import com.uam.hub.entity.DroneOperator;
import com.uam.hub.service.DroneOperatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drone-operators")
public class DroneOperatorController {

    private final DroneOperatorService operatorService;

    public DroneOperatorController(DroneOperatorService operatorService) {
        this.operatorService = operatorService;
    }

    @GetMapping
    public ResponseEntity<List<DroneOperator>> getAllOperators() {
        return ResponseEntity.ok(operatorService.getAllOperators());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DroneOperator> getOperatorById(@PathVariable Long id) {
        return ResponseEntity.ok(operatorService.getOperatorById(id));
    }

    @PostMapping
    public ResponseEntity<DroneOperator> createOperator(@RequestBody DroneOperator operator) {
        DroneOperator createdOperator = operatorService.createOperator(operator);
        return new ResponseEntity<>(createdOperator, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DroneOperator> updateOperator(@PathVariable Long id, @RequestBody DroneOperator operatorDetails) {
        DroneOperator updatedOperator = operatorService.updateOperator(id, operatorDetails);
        return ResponseEntity.ok(updatedOperator);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOperator(@PathVariable Long id) {
        operatorService.deleteOperator(id);
        return ResponseEntity.noContent().build();
    }
}
