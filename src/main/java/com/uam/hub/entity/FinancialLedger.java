package com.uam.hub.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "financial_ledgers")
@com.fasterxml.jackson.annotation.JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FinancialLedger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime entryDate;
    private String entryType; // REVENUE, EXPENSE
    private String journalType; // SALES, PURCHASE, BANK, CASH
    private String accountCode;
    private Double amount;
    private Double debit;
    private Double credit;
    private String category; // DOCKING_FEE, MAINTENANCE, FUEL, EQUIPMENT, PERMIT
    private String description;
    private String referenceId;
    private String referenceType; // INVOICE, BILL, PAYMENT

    public FinancialLedger() {}

    public FinancialLedger(LocalDateTime entryDate, String entryType, Double amount, String category, String description, String referenceId, String referenceType) {
        this.entryDate = entryDate;
        this.entryType = entryType;
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.referenceId = referenceId;
        this.referenceType = referenceType;
        if ("REVENUE".equalsIgnoreCase(entryType)) {
            this.journalType = "SALES";
            this.credit = amount;
            this.debit = 0.0;
        } else {
            this.journalType = "PURCHASE";
            this.debit = amount;
            this.credit = 0.0;
        }
    }

    public FinancialLedger(LocalDateTime entryDate, String entryType, String journalType, String accountCode, Double amount, Double debit, Double credit, String category, String description, String referenceId, String referenceType) {
        this.entryDate = entryDate;
        this.entryType = entryType;
        this.journalType = journalType;
        this.accountCode = accountCode;
        this.amount = amount;
        this.debit = debit;
        this.credit = credit;
        this.category = category;
        this.description = description;
        this.referenceId = referenceId;
        this.referenceType = referenceType;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getEntryDate() { return entryDate; }
    public void setEntryDate(LocalDateTime entryDate) { this.entryDate = entryDate; }

    public String getEntryType() { return entryType; }
    public void setEntryType(String entryType) { this.entryType = entryType; }

    public String getJournalType() { return journalType; }
    public void setJournalType(String journalType) { this.journalType = journalType; }

    public String getAccountCode() { return accountCode; }
    public void setAccountCode(String accountCode) { this.accountCode = accountCode; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public Double getDebit() { return debit; }
    public void setDebit(Double debit) { this.debit = debit; }

    public Double getCredit() { return credit; }
    public void setCredit(Double credit) { this.credit = credit; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getReferenceId() { return referenceId; }
    public void setReferenceId(String referenceId) { this.referenceId = referenceId; }

    public String getReferenceType() { return referenceType; }
    public void setReferenceType(String referenceType) { this.referenceType = referenceType; }
}
