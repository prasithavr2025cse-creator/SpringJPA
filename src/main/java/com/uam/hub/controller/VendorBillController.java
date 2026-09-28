package com.uam.hub.controller;

import com.uam.hub.entity.VendorBill;
import com.uam.hub.service.VendorBillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendor-bills")
@CrossOrigin(origins = "*")
public class VendorBillController {

    private final VendorBillService billService;

    public VendorBillController(VendorBillService billService) {
        this.billService = billService;
    }

    @GetMapping
    public ResponseEntity<List<VendorBill>> getAllVendorBills() {
        return ResponseEntity.ok(billService.getAllVendorBills());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendorBill> getVendorBillById(@PathVariable Long id) {
        return ResponseEntity.ok(billService.getVendorBillById(id));
    }

    @PostMapping
    public ResponseEntity<VendorBill> createVendorBill(@RequestBody VendorBill bill) {
        VendorBill created = billService.createVendorBill(bill);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VendorBill> updateVendorBill(@PathVariable Long id, @RequestBody VendorBill details) {
        VendorBill updated = billService.updateVendorBill(id, details);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendorBill(@PathVariable Long id) {
        billService.deleteVendorBill(id);
        return ResponseEntity.noContent().build();
    }
}
