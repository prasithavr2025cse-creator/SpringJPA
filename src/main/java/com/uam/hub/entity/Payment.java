package com.uam.hub.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String transactionReference;

    private LocalDateTime paymentDate;
    private Double amount;
    private String paymentMethod; // CREDIT_CARD, BANK_TRANSFER, DIGITAL_WALLET
    private String paymentType; // CUSTOMER_INCOME, VENDOR_EXPENSE

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_invoice_id")
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties("payments")
    private CustomerInvoice customerInvoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_bill_id")
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties("payments")
    private VendorBill vendorBill;


    public Payment() {}

    public Payment(String transactionReference, LocalDateTime paymentDate, Double amount, String paymentMethod, String paymentType, CustomerInvoice customerInvoice, VendorBill vendorBill) {
        this.transactionReference = transactionReference;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentType = paymentType;
        this.customerInvoice = customerInvoice;
        this.vendorBill = vendorBill;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTransactionReference() { return transactionReference; }
    public void setTransactionReference(String transactionReference) { this.transactionReference = transactionReference; }

    public LocalDateTime getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getPaymentType() { return paymentType; }
    public void setPaymentType(String paymentType) { this.paymentType = paymentType; }

    public CustomerInvoice getCustomerInvoice() { return customerInvoice; }
    public void setCustomerInvoice(CustomerInvoice customerInvoice) { this.customerInvoice = customerInvoice; }

    public VendorBill getVendorBill() { return vendorBill; }
    public void setVendorBill(VendorBill vendorBill) { this.vendorBill = vendorBill; }
}
