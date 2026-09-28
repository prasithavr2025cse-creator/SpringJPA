package com.uam.hub.service;

import com.uam.hub.entity.LandingPad;
import com.uam.hub.exception.ResourceNotFoundException;
import com.uam.hub.repository.LandingPadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LandingPadService {

    private final LandingPadRepository landingPadRepository;

    public LandingPadService(LandingPadRepository landingPadRepository) {
        this.landingPadRepository = landingPadRepository;
    }

    public List<LandingPad> getAllLandingPads() {
        return landingPadRepository.findAll();
    }

    public LandingPad getLandingPadById(Long id) {
        return landingPadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LandingPad", "id", id));
    }

    public LandingPad createLandingPad(LandingPad landingPad) {
        if (landingPad.getStatus() == null) {
            landingPad.setStatus("AVAILABLE");
        }
        return landingPadRepository.save(landingPad);
    }

    public LandingPad updateLandingPad(Long id, LandingPad padDetails) {
        LandingPad pad = getLandingPadById(id);
        if (padDetails.getPadCode() != null) pad.setPadCode(padDetails.getPadCode());
        if (padDetails.getLocationName() != null) pad.setLocationName(padDetails.getLocationName());
        if (padDetails.getLatitude() != null) pad.setLatitude(padDetails.getLatitude());
        if (padDetails.getLongitude() != null) pad.setLongitude(padDetails.getLongitude());
        if (padDetails.getStatus() != null) pad.setStatus(padDetails.getStatus());
        if (padDetails.getMaxWeightCapacity() != null) pad.setMaxWeightCapacity(padDetails.getMaxWeightCapacity());
        return landingPadRepository.save(pad);
    }

    public void deleteLandingPad(Long id) {
        LandingPad pad = getLandingPadById(id);
        landingPadRepository.delete(pad);
    }
}
