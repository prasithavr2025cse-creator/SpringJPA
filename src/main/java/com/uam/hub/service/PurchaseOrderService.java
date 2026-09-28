package com.uam.hub.service;

import com.uam.hub.entity.PurchaseOrder;
import com.uam.hub.exception.ResourceNotFoundException;
import com.uam.hub.repository.PurchaseOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PurchaseOrderService {

    private final PurchaseOrderRepository poRepository;

    public PurchaseOrderService(PurchaseOrderRepository poRepository) {
        this.poRepository = poRepository;
    }

    public List<PurchaseOrder> getAllPurchaseOrders() {
        return poRepository.findAll();
    }

    public PurchaseOrder getPurchaseOrderById(Long id) {
        return poRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PurchaseOrder", "id", id));
    }

    public PurchaseOrder createPurchaseOrder(PurchaseOrder po) {
        if (po.getOrderDate() == null) po.setOrderDate(LocalDateTime.now());
        if (po.getStatus() == null) po.setStatus("APPROVED");
        return poRepository.save(po);
    }

    public PurchaseOrder updatePurchaseOrder(Long id, PurchaseOrder details) {
        PurchaseOrder po = getPurchaseOrderById(id);
        if (details.getPoNumber() != null) po.setPoNumber(details.getPoNumber());
        if (details.getExpectedDeliveryDate() != null) po.setExpectedDeliveryDate(details.getExpectedDeliveryDate());
        if (details.getTotalAmount() != null) po.setTotalAmount(details.getTotalAmount());
        if (details.getStatus() != null) po.setStatus(details.getStatus());
        if (details.getDescription() != null) po.setDescription(details.getDescription());
        return poRepository.save(po);
    }

    public void deletePurchaseOrder(Long id) {
        PurchaseOrder po = getPurchaseOrderById(id);
        poRepository.delete(po);
    }
}
