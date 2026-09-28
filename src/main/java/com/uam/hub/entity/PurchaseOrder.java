package com.uam.hub.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchase_orders")
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String poNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties({"purchaseOrders", "vendorBills"})
    private Vendor vendor;

    private LocalDateTime orderDate;
    private LocalDateTime expectedDeliveryDate;
    private Double totalAmount;
    private String status; // DRAFT, APPROVED, RECEIVED, CANCELLED
    private String description;

    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL)
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties("purchaseOrder")
    private List<VendorBill> vendorBills = new ArrayList<>();


    public PurchaseOrder() {}

    public PurchaseOrder(String poNumber, Vendor vendor, LocalDateTime orderDate, LocalDateTime expectedDeliveryDate, Double totalAmount, String status, String description) {
        this.poNumber = poNumber;
        this.vendor = vendor;
        this.orderDate = orderDate;
        this.expectedDeliveryDate = expectedDeliveryDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPoNumber() { return poNumber; }
    public void setPoNumber(String poNumber) { this.poNumber = poNumber; }

    public Vendor getVendor() { return vendor; }
    public void setVendor(Vendor vendor) { this.vendor = vendor; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public LocalDateTime getExpectedDeliveryDate() { return expectedDeliveryDate; }
    public void setExpectedDeliveryDate(LocalDateTime expectedDeliveryDate) { this.expectedDeliveryDate = expectedDeliveryDate; }

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<VendorBill> getVendorBills() { return vendorBills; }
    public void setVendorBills(List<VendorBill> vendorBills) { this.vendorBills = vendorBills; }
}
