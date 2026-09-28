package com.uam.hub.service;

import com.uam.hub.entity.Vendor;
import com.uam.hub.exception.ResourceNotFoundException;
import com.uam.hub.repository.VendorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VendorService {

    private final VendorRepository vendorRepository;

    public VendorService(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    public Vendor getVendorById(Long id) {
        return vendorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor", "id", id));
    }

    public Vendor createVendor(Vendor vendor) {
        if (vendor.getVendorType() == null) vendor.setVendorType("MAINTENANCE");
        return vendorRepository.save(vendor);
    }

    public Vendor updateVendor(Long id, Vendor details) {
        Vendor vendor = getVendorById(id);
        if (details.getName() != null) vendor.setName(details.getName());
        if (details.getVendorType() != null) vendor.setVendorType(details.getVendorType());
        if (details.getContactEmail() != null) vendor.setContactEmail(details.getContactEmail());
        if (details.getPhone() != null) vendor.setPhone(details.getPhone());
        if (details.getAddress() != null) vendor.setAddress(details.getAddress());
        return vendorRepository.save(vendor);
    }

    public void deleteVendor(Long id) {
        Vendor vendor = getVendorById(id);
        vendorRepository.delete(vendor);
    }
}
