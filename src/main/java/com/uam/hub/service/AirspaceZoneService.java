package com.uam.hub.service;

import com.uam.hub.entity.AirspaceZone;
import com.uam.hub.exception.ResourceNotFoundException;
import com.uam.hub.repository.AirspaceZoneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AirspaceZoneService {

    private final AirspaceZoneRepository zoneRepository;

    public AirspaceZoneService(AirspaceZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    public List<AirspaceZone> getAllZones() {
        return zoneRepository.findAll();
    }

    public List<AirspaceZone> getActiveZones() {
        return zoneRepository.findByIsActive(true);
    }

    public AirspaceZone getZoneById(Long id) {
        return zoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AirspaceZone", "id", id));
    }

    public AirspaceZone getZoneByCode(String zoneCode) {
        return zoneRepository.findByZoneCode(zoneCode)
                .orElseThrow(() -> new ResourceNotFoundException("AirspaceZone", "zoneCode", zoneCode));
    }

    public AirspaceZone createZone(AirspaceZone zone) {
        if (zone.getIsActive() == null) {
            zone.setIsActive(true);
        }
        if (zone.getZoneType() == null) {
            zone.setZoneType("CLEAR");
        }
        return zoneRepository.save(zone);
    }

    public AirspaceZone updateZone(Long id, AirspaceZone zoneDetails) {
        AirspaceZone zone = getZoneById(id);
        if (zoneDetails.getZoneCode() != null) zone.setZoneCode(zoneDetails.getZoneCode());
        if (zoneDetails.getName() != null) zone.setName(zoneDetails.getName());
        if (zoneDetails.getZoneType() != null) zone.setZoneType(zoneDetails.getZoneType());
        if (zoneDetails.getMinAltitude() != null) zone.setMinAltitude(zoneDetails.getMinAltitude());
        if (zoneDetails.getMaxAltitude() != null) zone.setMaxAltitude(zoneDetails.getMaxAltitude());
        if (zoneDetails.getIsActive() != null) zone.setIsActive(zoneDetails.getIsActive());
        return zoneRepository.save(zone);
    }

    public void deleteZone(Long id) {
        AirspaceZone zone = getZoneById(id);
        zoneRepository.delete(zone);
    }
}
