package com.uam.hub.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "airspace_zones")
@com.fasterxml.jackson.annotation.JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AirspaceZone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String zoneCode;

    @Column(nullable = false)
    private String name;

    private String zoneType; // RESTRICTED, CLEAR, CAUTION, NO_FLY
    private Double minAltitude; // in meters/feet
    private Double maxAltitude; // in meters/feet
    private Boolean isActive;

    public AirspaceZone() {}

    public AirspaceZone(String zoneCode, String name, String zoneType, Double minAltitude, Double maxAltitude, Boolean isActive) {
        this.zoneCode = zoneCode;
        this.name = name;
        this.zoneType = zoneType;
        this.minAltitude = minAltitude;
        this.maxAltitude = maxAltitude;
        this.isActive = isActive;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getZoneCode() { return zoneCode; }
    public void setZoneCode(String zoneCode) { this.zoneCode = zoneCode; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getZoneType() { return zoneType; }
    public void setZoneType(String zoneType) { this.zoneType = zoneType; }

    public Double getMinAltitude() { return minAltitude; }
    public void setMinAltitude(Double minAltitude) { this.minAltitude = minAltitude; }

    public Double getMaxAltitude() { return maxAltitude; }
    public void setMaxAltitude(Double maxAltitude) { this.maxAltitude = maxAltitude; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
