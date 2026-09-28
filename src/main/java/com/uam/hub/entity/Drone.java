package com.uam.hub.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "drones")
@com.fasterxml.jackson.annotation.JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Drone {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false, unique = true)
    private String registrationNumber;

    private Integer batteryCapacity; // in Wh or mAh
    private Integer currentBatteryLevel; // percentage 0-100
    private Double payloadCapacity; // in kg
    private String status; // AVAILABLE, IN_FLIGHT, DOCKED, MAINTENANCE

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operator_id")
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties("drones")
    private DroneOperator operator;


    @OneToMany(mappedBy = "drone", cascade = CascadeType.ALL)
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties({"drone", "landingPad", "customerInvoice"})
    private List<DockingTransaction> dockingTransactions = new ArrayList<>();



    public Drone() {}

    public Drone(String model, String registrationNumber, Integer batteryCapacity, Integer currentBatteryLevel, Double payloadCapacity, String status, DroneOperator operator) {
        this.model = model;
        this.registrationNumber = registrationNumber;
        this.batteryCapacity = batteryCapacity;
        this.currentBatteryLevel = currentBatteryLevel;
        this.payloadCapacity = payloadCapacity;
        this.status = status;
        this.operator = operator;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }

    public Integer getBatteryCapacity() { return batteryCapacity; }
    public void setBatteryCapacity(Integer batteryCapacity) { this.batteryCapacity = batteryCapacity; }

    public Integer getCurrentBatteryLevel() { return currentBatteryLevel; }
    public void setCurrentBatteryLevel(Integer currentBatteryLevel) { this.currentBatteryLevel = currentBatteryLevel; }

    public Double getPayloadCapacity() { return payloadCapacity; }
    public void setPayloadCapacity(Double payloadCapacity) { this.payloadCapacity = payloadCapacity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public DroneOperator getOperator() { return operator; }
    public void setOperator(DroneOperator operator) { this.operator = operator; }

    public List<DockingTransaction> getDockingTransactions() { return dockingTransactions; }
    public void setDockingTransactions(List<DockingTransaction> dockingTransactions) { this.dockingTransactions = dockingTransactions; }
}
