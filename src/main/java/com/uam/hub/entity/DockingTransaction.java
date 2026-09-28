package com.uam.hub.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "docking_transactions")
@com.fasterxml.jackson.annotation.JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DockingTransaction {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drone_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties("dockingTransactions")
    private Drone drone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landing_pad_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties("dockingTransactions")
    private LandingPad landingPad;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status; // RESERVED, ACTIVE, COMPLETED, CANCELLED
    private Double feeAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_invoice_id")
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties("dockingTransactions")
    private CustomerInvoice customerInvoice;


    public DockingTransaction() {}

    public DockingTransaction(Drone drone, LandingPad landingPad, LocalDateTime startTime, LocalDateTime endTime, String status, Double feeAmount, CustomerInvoice customerInvoice) {
        this.drone = drone;
        this.landingPad = landingPad;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.feeAmount = feeAmount;
        this.customerInvoice = customerInvoice;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Drone getDrone() { return drone; }
    public void setDrone(Drone drone) { this.drone = drone; }

    public LandingPad getLandingPad() { return landingPad; }
    public void setLandingPad(LandingPad landingPad) { this.landingPad = landingPad; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getFeeAmount() { return feeAmount; }
    public void setFeeAmount(Double feeAmount) { this.feeAmount = feeAmount; }

    public CustomerInvoice getCustomerInvoice() { return customerInvoice; }
    public void setCustomerInvoice(CustomerInvoice customerInvoice) { this.customerInvoice = customerInvoice; }
}
