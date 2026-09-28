package com.uam.hub.controller;

import com.uam.hub.entity.*;
import com.uam.hub.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
public class WebController {

    private final LandingPadService landingPadService;
    private final DroneService droneService;
    private final DroneOperatorService droneOperatorService;
    private final AirspaceZoneService airspaceZoneService;
    private final DockingTransactionService dockingTransactionService;
    private final ProductService productService;
    private final AccountService accountService;
    private final SalesOrderService salesOrderService;
    private final CustomerInvoiceService customerInvoiceService;
    private final PurchaseOrderService purchaseOrderService;
    private final VendorBillService vendorBillService;
    private final PaymentService paymentService;
    private final VendorService vendorService;
    private final BudgetService budgetService;
    private final FinancialLedgerService financialLedgerService;
    private final FinancialReportService financialReportService;

    public WebController(
            LandingPadService landingPadService,
            DroneService droneService,
            DroneOperatorService droneOperatorService,
            AirspaceZoneService airspaceZoneService,
            DockingTransactionService dockingTransactionService,
            ProductService productService,
            AccountService accountService,
            SalesOrderService salesOrderService,
            CustomerInvoiceService customerInvoiceService,
            PurchaseOrderService purchaseOrderService,
            VendorBillService vendorBillService,
            PaymentService paymentService,
            VendorService vendorService,
            BudgetService budgetService,
            FinancialLedgerService financialLedgerService,
            FinancialReportService financialReportService) {
        this.landingPadService = landingPadService;
        this.droneService = droneService;
        this.droneOperatorService = droneOperatorService;
        this.airspaceZoneService = airspaceZoneService;
        this.dockingTransactionService = dockingTransactionService;
        this.productService = productService;
        this.accountService = accountService;
        this.salesOrderService = salesOrderService;
        this.customerInvoiceService = customerInvoiceService;
        this.purchaseOrderService = purchaseOrderService;
        this.vendorBillService = vendorBillService;
        this.paymentService = paymentService;
        this.vendorService = vendorService;
        this.budgetService = budgetService;
        this.financialLedgerService = financialLedgerService;
        this.financialReportService = financialReportService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<LandingPad> landingPads = landingPadService.getAllLandingPads();
        List<Drone> drones = droneService.getAllDrones();
        List<AirspaceZone> zones = airspaceZoneService.getAllZones();
        List<DockingTransaction> dockingTransactions = dockingTransactionService.getAllTransactions();

        long activeDronesCount = drones.stream().filter(d -> !"MAINTENANCE".equalsIgnoreCase(d.getStatus())).count();
        long availablePadsCount = landingPads.stream().filter(p -> "AVAILABLE".equalsIgnoreCase(p.getStatus())).count();
        long activeZonesCount = zones.stream().filter(z -> Boolean.TRUE.equals(z.getIsActive())).count();
        long activeDockingCount = dockingTransactions.stream().filter(t -> "ACTIVE".equalsIgnoreCase(t.getStatus())).count();

        Map<String, Object> pnl = financialReportService.getProfitAndLoss();
        @SuppressWarnings("unchecked")
        Map<String, Object> revMap = (Map<String, Object>) pnl.get("revenue");
        double totalRevenue = (revMap != null && revMap.get("Total Revenue") instanceof Number n) ? n.doubleValue() : 0.0;

        @SuppressWarnings("unchecked")
        Map<String, Object> expMap = (Map<String, Object>) pnl.get("expenses");
        double totalExpenses = (expMap != null && expMap.get("Total Expenses") instanceof Number n) ? n.doubleValue() : 0.0;

        double netProfit = (pnl.get("netProfitOrLoss") instanceof Number n) ? n.doubleValue() : (totalRevenue - totalExpenses);

        model.addAttribute("projectName", "UAM HUB");
        model.addAttribute("dashboardTitle", "AIR MOBILITY LOGISTICS & FINANCIAL COMMAND CENTER");
        model.addAttribute("serverStatus", "ONLINE");
        model.addAttribute("systemTime", LocalDateTime.now().toString());

        model.addAttribute("landingPads", landingPads);
        model.addAttribute("drones", drones);
        model.addAttribute("zones", zones);
        model.addAttribute("dockingTransactions", dockingTransactions);

        model.addAttribute("totalDrones", drones.size());
        model.addAttribute("activeDronesCount", activeDronesCount);
        model.addAttribute("totalPads", landingPads.size());
        model.addAttribute("availablePadsCount", availablePadsCount);
        model.addAttribute("totalZones", zones.size());
        model.addAttribute("activeZonesCount", activeZonesCount);
        model.addAttribute("activeDockingCount", activeDockingCount);

        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("totalExpenses", totalExpenses);
        model.addAttribute("netProfit", netProfit);

        model.addAttribute("activePage", "dashboard");
        return "index";
    }

    @GetMapping("/landing-pads")
    public String landingPads(Model model) {
        List<LandingPad> landingPads = landingPadService.getAllLandingPads();
        long availableCount = landingPads.stream().filter(p -> "AVAILABLE".equalsIgnoreCase(p.getStatus())).count();
        long occupiedCount = landingPads.stream().filter(p -> "OCCUPIED".equalsIgnoreCase(p.getStatus())).count();
        long maintenanceCount = landingPads.stream().filter(p -> "MAINTENANCE".equalsIgnoreCase(p.getStatus())).count();

        model.addAttribute("landingPads", landingPads);
        model.addAttribute("totalPads", landingPads.size());
        model.addAttribute("availableCount", availableCount);
        model.addAttribute("occupiedCount", occupiedCount);
        model.addAttribute("maintenanceCount", maintenanceCount);
        model.addAttribute("activePage", "landing-pads");
        return "landing-pads";
    }

    @GetMapping("/drones")
    public String drones(Model model) {
        List<Drone> drones = droneService.getAllDrones();
        List<DroneOperator> operators = droneOperatorService.getAllOperators();

        long availableCount = drones.stream().filter(d -> "AVAILABLE".equalsIgnoreCase(d.getStatus())).count();
        long inFlightCount = drones.stream().filter(d -> "IN_FLIGHT".equalsIgnoreCase(d.getStatus())).count();
        long dockedCount = drones.stream().filter(d -> "DOCKED".equalsIgnoreCase(d.getStatus())).count();
        long maintenanceCount = drones.stream().filter(d -> "MAINTENANCE".equalsIgnoreCase(d.getStatus())).count();

        model.addAttribute("drones", drones);
        model.addAttribute("operators", operators);
        model.addAttribute("totalDrones", drones.size());
        model.addAttribute("availableCount", availableCount);
        model.addAttribute("inFlightCount", inFlightCount);
        model.addAttribute("dockedCount", dockedCount);
        model.addAttribute("maintenanceCount", maintenanceCount);
        model.addAttribute("activePage", "drones");
        return "drones";
    }

    @GetMapping("/docking")
    public String docking(Model model) {
        List<DockingTransaction> dockingTransactions = dockingTransactionService.getAllTransactions();
        List<Drone> drones = droneService.getAllDrones();
        List<LandingPad> landingPads = landingPadService.getAllLandingPads();

        long activeCount = dockingTransactions.stream().filter(t -> "ACTIVE".equalsIgnoreCase(t.getStatus())).count();
        long completedCount = dockingTransactions.stream().filter(t -> "COMPLETED".equalsIgnoreCase(t.getStatus())).count();
        double totalFees = dockingTransactions.stream()
                .mapToDouble(t -> t.getFeeAmount() != null ? t.getFeeAmount() : 0.0)
                .sum();

        model.addAttribute("dockingTransactions", dockingTransactions);
        model.addAttribute("drones", drones);
        model.addAttribute("landingPads", landingPads);
        model.addAttribute("activeCount", activeCount);
        model.addAttribute("completedCount", completedCount);
        model.addAttribute("totalFees", totalFees);
        model.addAttribute("activePage", "docking");
        return "docking";
    }

    @GetMapping("/airspace")
    public String airspace(Model model) {
        List<AirspaceZone> zones = airspaceZoneService.getAllZones();

        long activeZonesCount = zones.stream().filter(z -> Boolean.TRUE.equals(z.getIsActive())).count();
        long clearCount = zones.stream().filter(z -> "CLEAR".equalsIgnoreCase(z.getZoneType())).count();
        long cautionCount = zones.stream().filter(z -> "CAUTION".equalsIgnoreCase(z.getZoneType())).count();
        long restrictedCount = zones.stream().filter(z -> "RESTRICTED".equalsIgnoreCase(z.getZoneType()) || "NO_FLY".equalsIgnoreCase(z.getZoneType())).count();

        model.addAttribute("zones", zones);
        model.addAttribute("totalZones", zones.size());
        model.addAttribute("activeZonesCount", activeZonesCount);
        model.addAttribute("clearCount", clearCount);
        model.addAttribute("cautionCount", cautionCount);
        model.addAttribute("restrictedCount", restrictedCount);
        model.addAttribute("activePage", "airspace");
        return "airspace";
    }

    @GetMapping("/finance")
    public String finance(Model model, @RequestParam(value = "tab", required = false, defaultValue = "products") String tab) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("accounts", accountService.getAllAccounts());
        model.addAttribute("salesOrders", salesOrderService.getAllSalesOrders());
        model.addAttribute("invoices", customerInvoiceService.getAllInvoices());
        model.addAttribute("purchaseOrders", purchaseOrderService.getAllPurchaseOrders());
        model.addAttribute("vendorBills", vendorBillService.getAllVendorBills());
        model.addAttribute("payments", paymentService.getAllPayments());
        model.addAttribute("vendors", vendorService.getAllVendors());
        model.addAttribute("budgets", budgetService.getAllBudgets());
        model.addAttribute("ledgerEntries", financialLedgerService.getAllLedgerEntries());

        model.addAttribute("currentTab", tab);
        model.addAttribute("activePage", "finance");
        return "finance";
    }

    @GetMapping("/reports")
    public String reports(Model model) {
        Map<String, Object> balanceSheet = financialReportService.getBalanceSheet();
        Map<String, Object> profitAndLoss = financialReportService.getProfitAndLoss();
        List<Map<String, Object>> budgetVariance = financialReportService.getBudgetVarianceReport();

        model.addAttribute("balanceSheet", balanceSheet);
        model.addAttribute("profitAndLoss", profitAndLoss);
        model.addAttribute("budgetVariance", budgetVariance);
        model.addAttribute("activePage", "reports");
        return "reports";
    }
}
