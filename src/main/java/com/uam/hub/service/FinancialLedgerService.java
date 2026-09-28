package com.uam.hub.service;

import com.uam.hub.entity.FinancialLedger;
import com.uam.hub.exception.ResourceNotFoundException;
import com.uam.hub.repository.FinancialLedgerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class FinancialLedgerService {

    private final FinancialLedgerRepository ledgerRepository;

    public FinancialLedgerService(FinancialLedgerRepository ledgerRepository) {
        this.ledgerRepository = ledgerRepository;
    }

    public List<FinancialLedger> getAllLedgerEntries() {
        return ledgerRepository.findAll();
    }

    public FinancialLedger getLedgerEntryById(Long id) {
        return ledgerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FinancialLedger", "id", id));
    }

    public FinancialLedger createLedgerEntry(FinancialLedger entry) {
        if (entry.getEntryDate() == null) entry.setEntryDate(LocalDateTime.now());
        if (entry.getJournalType() == null) {
            entry.setJournalType("REVENUE".equalsIgnoreCase(entry.getEntryType()) ? "SALES" : "PURCHASE");
        }
        if (entry.getDebit() == null) entry.setDebit(0.0);
        if (entry.getCredit() == null) entry.setCredit(0.0);
        if (entry.getAmount() == null) {
            entry.setAmount(Math.max(entry.getDebit(), entry.getCredit()));
        }
        return ledgerRepository.save(entry);
    }

    public FinancialLedger updateLedgerEntry(Long id, FinancialLedger details) {
        FinancialLedger entry = getLedgerEntryById(id);
        if (details.getEntryType() != null) entry.setEntryType(details.getEntryType());
        if (details.getJournalType() != null) entry.setJournalType(details.getJournalType());
        if (details.getAccountCode() != null) entry.setAccountCode(details.getAccountCode());
        if (details.getAmount() != null) entry.setAmount(details.getAmount());
        if (details.getDebit() != null) entry.setDebit(details.getDebit());
        if (details.getCredit() != null) entry.setCredit(details.getCredit());
        if (details.getCategory() != null) entry.setCategory(details.getCategory());
        if (details.getDescription() != null) entry.setDescription(details.getDescription());
        if (details.getReferenceId() != null) entry.setReferenceId(details.getReferenceId());
        return ledgerRepository.save(entry);
    }

    public void deleteLedgerEntry(Long id) {
        FinancialLedger entry = getLedgerEntryById(id);
        ledgerRepository.delete(entry);
    }
}
