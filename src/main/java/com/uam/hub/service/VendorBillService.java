package com.uam.hub.service;

import com.uam.hub.entity.VendorBill;
import com.uam.hub.exception.ResourceNotFoundException;
import com.uam.hub.repository.VendorBillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class VendorBillService {

    private final VendorBillRepository billRepository;

    public VendorBillService(VendorBillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public List<VendorBill> getAllVendorBills() {
        return billRepository.findAll();
    }

    public VendorBill getVendorBillById(Long id) {
        return billRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("VendorBill", "id", id));
    }

    public VendorBill createVendorBill(VendorBill bill) {
        if (bill.getBillDate() == null) bill.setBillDate(LocalDateTime.now());
        if (bill.getDueDate() == null) bill.setDueDate(LocalDateTime.now().plusDays(30));
        if (bill.getStatus() == null) bill.setStatus("PENDING");
        return billRepository.save(bill);
    }

    public VendorBill updateVendorBill(Long id, VendorBill details) {
        VendorBill bill = getVendorBillById(id);
        if (details.getBillNumber() != null) bill.setBillNumber(details.getBillNumber());
        if (details.getDueDate() != null) bill.setDueDate(details.getDueDate());
        if (details.getTotalAmount() != null) bill.setTotalAmount(details.getTotalAmount());
        if (details.getStatus() != null) bill.setStatus(details.getStatus());
        if (details.getDescription() != null) bill.setDescription(details.getDescription());
        return billRepository.save(bill);
    }

    public void deleteVendorBill(Long id) {
        VendorBill bill = getVendorBillById(id);
        billRepository.delete(bill);
    }
}
