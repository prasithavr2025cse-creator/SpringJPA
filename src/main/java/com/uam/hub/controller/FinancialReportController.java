package com.uam.hub.controller;

import com.uam.hub.service.FinancialReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class FinancialReportController {

    private final FinancialReportService reportService;

    public FinancialReportController(FinancialReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/balance-sheet")
    public ResponseEntity<Map<String, Object>> getBalanceSheet() {
        return ResponseEntity.ok(reportService.getBalanceSheet());
    }

    @GetMapping("/profit-loss")
    public ResponseEntity<Map<String, Object>> getProfitAndLoss() {
        return ResponseEntity.ok(reportService.getProfitAndLoss());
    }

    @GetMapping("/budget-variance")
    public ResponseEntity<List<Map<String, Object>>> getBudgetVarianceReport() {
        return ResponseEntity.ok(reportService.getBudgetVarianceReport());
    }
}
