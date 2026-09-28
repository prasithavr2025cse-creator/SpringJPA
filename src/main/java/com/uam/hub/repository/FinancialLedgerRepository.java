package com.uam.hub.repository;

import com.uam.hub.entity.FinancialLedger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinancialLedgerRepository extends JpaRepository<FinancialLedger, Long> {
    List<FinancialLedger> findByEntryType(String entryType);
}
