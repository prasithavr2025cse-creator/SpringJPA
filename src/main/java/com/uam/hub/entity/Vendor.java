package com.uam.hub.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vendors")
@com.fasterxml.jackson.annotation.JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String vendorType; // MAINTENANCE, FUEL_CHARGING, LOGISTICS, PARTS
    private String contactEmail;
    private String phone;
    private String address;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL)
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties("vendor")
    private List<VendorBill> vendorBills = new ArrayList<>();

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL)
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties("vendor")
    private List<PurchaseOrder> purchaseOrders = new ArrayList<>();


    public Vendor() {}

    public Vendor(String name, String vendorType, String contactEmail, String phone, String address) {
        this.name = name;
        this.vendorType = vendorType;
        this.contactEmail = contactEmail;
        this.phone = phone;
        this.address = address;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getVendorType() { return vendorType; }
    public void setVendorType(String vendorType) { this.vendorType = vendorType; }

    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public List<VendorBill> getVendorBills() { return vendorBills; }
    public void setVendorBills(List<VendorBill> vendorBills) { this.vendorBills = vendorBills; }

    public List<PurchaseOrder> getPurchaseOrders() { return purchaseOrders; }
    public void setPurchaseOrders(List<PurchaseOrder> purchaseOrders) { this.purchaseOrders = purchaseOrders; }
}
