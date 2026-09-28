package com.uam.hub.repository;

import com.uam.hub.entity.DockingTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DockingTransactionRepository extends JpaRepository<DockingTransaction, Long> {
    List<DockingTransaction> findByStatus(String status);
}
