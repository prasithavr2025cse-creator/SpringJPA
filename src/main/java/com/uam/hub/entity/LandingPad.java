package com.uam.hub.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "landing_pads")
@com.fasterxml.jackson.annotation.JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LandingPad {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String padCode;

    private String locationName;
    private Double latitude;
    private Double longitude;
    private String status; // AVAILABLE, OCCUPIED, RESERVED, OUT_OF_SERVICE
    private Double maxWeightCapacity; // in kg

    @OneToMany(mappedBy = "landingPad", cascade = CascadeType.ALL)
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties({"landingPad", "drone", "customerInvoice"})
    private List<DockingTransaction> dockingTransactions = new ArrayList<>();


    public LandingPad() {}

    public LandingPad(String padCode, String locationName, Double latitude, Double longitude, String status, Double maxWeightCapacity) {
        this.padCode = padCode;
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.maxWeightCapacity = maxWeightCapacity;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPadCode() { return padCode; }
    public void setPadCode(String padCode) { this.padCode = padCode; }

    public String getLocationName() { return locationName; }
    public void setLocationName(String locationName) { this.locationName = locationName; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getMaxWeightCapacity() { return maxWeightCapacity; }
    public void setMaxWeightCapacity(Double maxWeightCapacity) { this.maxWeightCapacity = maxWeightCapacity; }

    public List<DockingTransaction> getDockingTransactions() { return dockingTransactions; }
    public void setDockingTransactions(List<DockingTransaction> dockingTransactions) { this.dockingTransactions = dockingTransactions; }
}
