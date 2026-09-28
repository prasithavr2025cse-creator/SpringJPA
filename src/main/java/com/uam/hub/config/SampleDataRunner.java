package com.uam.hub.config;

import com.uam.hub.entity.*;
import com.uam.hub.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class SampleDataRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(SampleDataRunner.class);

    private final DroneOperatorRepository operatorRepository;
    private final DroneRepository droneRepository;
    private final LandingPadRepository landingPadRepository;
    private final VendorRepository vendorRepository;
    private final AirspaceZoneRepository zoneRepository;
    private final DockingTransactionRepository dockingRepository;
    private final CustomerInvoiceRepository invoiceRepository;
    private final VendorBillRepository billRepository;
    private final PaymentRepository paymentRepository;
    private final FinancialLedgerRepository ledgerRepository;
    private final BudgetRepository budgetRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ProductRepository productRepository;
    private final AccountRepository accountRepository;
    private final SalesOrderRepository salesOrderRepository;

    public SampleDataRunner(
            DroneOperatorRepository operatorRepository,
            DroneRepository droneRepository,
            LandingPadRepository landingPadRepository,
            VendorRepository vendorRepository,
            AirspaceZoneRepository zoneRepository,
            DockingTransactionRepository dockingRepository,
            CustomerInvoiceRepository invoiceRepository,
            VendorBillRepository billRepository,
            PaymentRepository paymentRepository,
            FinancialLedgerRepository ledgerRepository,
            BudgetRepository budgetRepository,
            PurchaseOrderRepository purchaseOrderRepository,
            ProductRepository productRepository,
            AccountRepository accountRepository,
            SalesOrderRepository salesOrderRepository) {
        this.operatorRepository = operatorRepository;
        this.droneRepository = droneRepository;
        this.landingPadRepository = landingPadRepository;
        this.vendorRepository = vendorRepository;
        this.zoneRepository = zoneRepository;
        this.dockingRepository = dockingRepository;
        this.invoiceRepository = invoiceRepository;
        this.billRepository = billRepository;
        this.paymentRepository = paymentRepository;
        this.ledgerRepository = ledgerRepository;
        this.budgetRepository = budgetRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.productRepository = productRepository;
        this.accountRepository = accountRepository;
        this.salesOrderRepository = salesOrderRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (productRepository.count() == 0) {
            log.info("Seeding UAM Service Products...");
            productRepository.save(new Product("PROD-DOCK", "Vertiport Docking Fee", "Hourly landing pad reservation & docking fee", "SERVICE", 75.00, true));
            productRepository.save(new Product("PROD-CHARGE", "Emergency Rapid Charge", "High-voltage rapid battery recharge service", "SERVICE", 120.00, true));
            productRepository.save(new Product("PROD-NAV", "Airspace Navigation Fee", "Per-flight airway corridor telemetry tracking fee", "SERVICE", 50.00, true));
        }

        if (accountRepository.count() == 0) {
            log.info("Seeding Chart of Accounts...");
            accountRepository.save(new Account("1000", "Cash", "ASSET", "Main operational cash on hand", true));
            accountRepository.save(new Account("1010", "Bank", "ASSET", "Primary corporate bank account", true));
            accountRepository.save(new Account("1100", "Accounts Receivable", "ASSET", "Pending drone operator invoice balances", true));
            accountRepository.save(new Account("1200", "Vertiport Landing Infrastructure", "ASSET", "Vertiport physical pads & radar hardware", true));
            accountRepository.save(new Account("2000", "Accounts Payable", "LIABILITY", "Outstanding maintenance vendor bills", true));
            accountRepository.save(new Account("3000", "Owner Equity", "EQUITY", "Capital contribution & retained earnings", true));
            accountRepository.save(new Account("4000", "Docking Revenue", "INCOME", "Revenues from vertiport pad usage", true));
            accountRepository.save(new Account("4010", "Navigation Revenue", "INCOME", "Revenues from airspace corridor tracking", true));
            accountRepository.save(new Account("4020", "Charging Revenue", "INCOME", "Revenues from rapid battery charging", true));
            accountRepository.save(new Account("5000", "Maintenance Expense", "EXPENSE", "Vertiport pad & structure repair costs", true));
            accountRepository.save(new Account("5010", "Radar Calibration Expense", "EXPENSE", "Radar sensor tuning & compliance costs", true));
            accountRepository.save(new Account("5020", "Operations Expense", "EXPENSE", "Air mobility command center operational costs", true));
        }

        if (operatorRepository.count() > 0) {
            log.info("Base sample data already present in database.");
            return;
        }

        log.info("Initializing sample data for UAM Hub...");

        // 1. Drone Operators
        DroneOperator operator1 = new DroneOperator("AeroVelocity Logistics", "OP-LIC-2026-001", "ops@aerovelocity.com", "+1-555-0192", "ACTIVE");
        DroneOperator operator2 = new DroneOperator("SkyCargo Express", "OP-LIC-2026-002", "contact@skycargo.com", "+1-555-0843", "ACTIVE");
        operatorRepository.save(operator1);
        operatorRepository.save(operator2);

        // 2. Drones
        Drone drone1 = new Drone("SkyRider X4", "DRONE-REG-101", 5000, 95, 15.5, "AVAILABLE", operator1);
        Drone drone2 = new Drone("HeavyLifter Pro", "DRONE-REG-202", 12000, 80, 45.0, "IN_FLIGHT", operator1);
        Drone drone3 = new Drone("Falcon Courier 2", "DRONE-REG-303", 4000, 100, 10.0, "DOCKED", operator2);
        droneRepository.save(drone1);
        droneRepository.save(drone2);
        droneRepository.save(drone3);

        // 3. Landing Pads
        LandingPad pad1 = new LandingPad("PAD-NORTH-01", "Downtown Hub North Roof", 37.7749, -122.4194, "AVAILABLE", 50.0);
        LandingPad pad2 = new LandingPad("PAD-SOUTH-02", "South Port Logistics Bay", 37.7349, -122.3894, "OCCUPIED", 75.0);
        landingPadRepository.save(pad1);
        landingPadRepository.save(pad2);

        // 4. Airspace Zones
        AirspaceZone zone1 = new AirspaceZone("CORRIDOR-ALPHA", "North Metro Airway", "CLEAR", 100.0, 400.0, true);
        AirspaceZone zone2 = new AirspaceZone("RESTRICTED-HQ", "Government District No-Fly Zone", "NO_FLY", 0.0, 1000.0, true);
        zoneRepository.save(zone1);
        zoneRepository.save(zone2);

        // 5. Customer Invoices
        CustomerInvoice invoice1 = new CustomerInvoice("INV-2026-001", LocalDateTime.now().minusDays(2), LocalDateTime.now().plusDays(28), 350.00, "PAID", "Global Retail Logistics", "billing@globalretail.com");
        invoiceRepository.save(invoice1);

        // 6. Docking Transactions
        DockingTransaction transaction1 = new DockingTransaction(drone3, pad2, LocalDateTime.now().minusHours(3), LocalDateTime.now().minusHours(1), "COMPLETED", 150.00, invoice1);
        dockingRepository.save(transaction1);

        // 7. Vendors
        Vendor vendor1 = new Vendor("AeroTech Maintenance Co", "MAINTENANCE", "support@aerotech.com", "+1-800-555-9000", "100 Aviation Way, Flight City");
        Vendor vendor2 = new Vendor("VoltCharge Power Networks", "FUEL_CHARGING", "billing@voltcharge.net", "+1-800-555-3434", "500 Grid Avenue, Power City");
        vendorRepository.save(vendor1);
        vendorRepository.save(vendor2);

        // 8. Purchase Orders
        PurchaseOrder po1 = new PurchaseOrder("PO-2026-8801", vendor1, LocalDateTime.now().minusDays(5), LocalDateTime.now().plusDays(2), 2500.00, "APPROVED", "Replacement battery modules & rotors");
        purchaseOrderRepository.save(po1);

        // 9. Vendor Bills
        VendorBill bill1 = new VendorBill("BILL-2026-901", vendor1, po1, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(15), 2500.00, "PENDING", "Invoice for PO-2026-8801 components");
        billRepository.save(bill1);

        // 10. Payments
        Payment payment1 = new Payment("PAY-TXN-99001", LocalDateTime.now().minusDays(1), 350.00, "BANK_TRANSFER", "CUSTOMER_INCOME", invoice1, null);
        paymentRepository.save(payment1);

        // 11. Financial Ledgers
        FinancialLedger ledger1 = new FinancialLedger(LocalDateTime.now().minusDays(1), "REVENUE", "SALES", "4000", 350.00, 0.0, 350.00, "DOCKING_FEE", "Customer invoice payment received for INV-2026-001", "INV-2026-001", "INVOICE");
        FinancialLedger ledger2 = new FinancialLedger(LocalDateTime.now().minusDays(5), "EXPENSE", "PURCHASE", "5000", 2500.00, 2500.00, 0.0, "MAINTENANCE", "Purchase order PO-2026-8801 approved", "PO-2026-8801", "PURCHASE_ORDER");
        ledgerRepository.save(ledger1);
        ledgerRepository.save(ledger2);

        // 12. Budgets
        Budget budget1 = new Budget(2026, "Urban Airspace Sector 1", 500000.00, 125000.00, 375000.00, "APPROVED");
        Budget budget2 = new Budget(2026, "Vertiport Maintenance Zone 2", 200000.00, 45000.00, 155000.00, "APPROVED");
        budgetRepository.save(budget1);
        budgetRepository.save(budget2);

        // 13. Sales Orders
        Product prod1 = productRepository.findAll().get(0);
        SalesOrder order1 = new SalesOrder("SO-2026-701", operator1, LocalDateTime.now().minusDays(1), prod1, 5, 75.00, 375.00, "CONFIRMED");
        salesOrderRepository.save(order1);

        log.info("Sample data initialization completed successfully for UAM Hub!");
    }
}
