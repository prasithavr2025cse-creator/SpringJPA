package com.uam.hub.service;

import com.uam.hub.entity.Budget;
import com.uam.hub.exception.ResourceNotFoundException;
import com.uam.hub.repository.BudgetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BudgetService {

    private final BudgetRepository budgetRepository;

    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    public List<Budget> getAllBudgets() {
        List<Budget> budgets = budgetRepository.findAll();
        budgets.forEach(this::calculateVariance);
        return budgets;
    }

    public Budget getBudgetById(Long id) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget", "id", id));
        calculateVariance(budget);
        return budget;
    }

    public Budget createBudget(Budget budget) {
        if (budget.getStatus() == null) budget.setStatus("APPROVED");
        calculateVariance(budget);
        return budgetRepository.save(budget);
    }

    public Budget updateBudget(Long id, Budget details) {
        Budget budget = getBudgetById(id);
        if (details.getFiscalYear() != null) budget.setFiscalYear(details.getFiscalYear());
        if (details.getDepartment() != null) budget.setDepartment(details.getDepartment());
        if (details.getAllocatedAmount() != null) budget.setAllocatedAmount(details.getAllocatedAmount());
        if (details.getSpentAmount() != null) budget.setSpentAmount(details.getSpentAmount());
        if (details.getStatus() != null) budget.setStatus(details.getStatus());
        calculateVariance(budget);
        return budgetRepository.save(budget);
    }

    public void deleteBudget(Long id) {
        Budget budget = getBudgetById(id);
        budgetRepository.delete(budget);
    }

    private void calculateVariance(Budget budget) {
        double planned = budget.getAllocatedAmount() != null ? budget.getAllocatedAmount() : 0.0;
        double actual = budget.getSpentAmount() != null ? budget.getSpentAmount() : 0.0;
        budget.setRemainingAmount(planned - actual);
    }
}
