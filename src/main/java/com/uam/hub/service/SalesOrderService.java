package com.uam.hub.service;

import com.uam.hub.entity.SalesOrder;
import com.uam.hub.exception.ResourceNotFoundException;
import com.uam.hub.repository.SalesOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class SalesOrderService {

    private final SalesOrderRepository salesOrderRepository;

    public SalesOrderService(SalesOrderRepository salesOrderRepository) {
        this.salesOrderRepository = salesOrderRepository;
    }

    public List<SalesOrder> getAllSalesOrders() {
        return salesOrderRepository.findAll();
    }

    public SalesOrder getSalesOrderById(Long id) {
        return salesOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SalesOrder", "id", id));
    }

    public SalesOrder createSalesOrder(SalesOrder salesOrder) {
        if (salesOrder.getOrderDate() == null) salesOrder.setOrderDate(LocalDateTime.now());
        if (salesOrder.getStatus() == null) salesOrder.setStatus("CONFIRMED");
        if (salesOrder.getQuantity() != null && salesOrder.getUnitPrice() != null) {
            salesOrder.setTotalAmount(salesOrder.getQuantity() * salesOrder.getUnitPrice());
        }
        return salesOrderRepository.save(salesOrder);
    }

    public SalesOrder updateSalesOrder(Long id, SalesOrder details) {
        SalesOrder order = getSalesOrderById(id);
        if (details.getOrderNumber() != null) order.setOrderNumber(details.getOrderNumber());
        if (details.getQuantity() != null) order.setQuantity(details.getQuantity());
        if (details.getUnitPrice() != null) order.setUnitPrice(details.getUnitPrice());
        if (details.getStatus() != null) order.setStatus(details.getStatus());
        if (order.getQuantity() != null && order.getUnitPrice() != null) {
            order.setTotalAmount(order.getQuantity() * order.getUnitPrice());
        }
        return salesOrderRepository.save(order);
    }

    public void deleteSalesOrder(Long id) {
        SalesOrder order = getSalesOrderById(id);
        salesOrderRepository.delete(order);
    }
}
