package com.uam.hub.service;

import com.uam.hub.entity.CustomerInvoice;
import com.uam.hub.exception.ResourceNotFoundException;
import com.uam.hub.repository.CustomerInvoiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CustomerInvoiceService {

    private final CustomerInvoiceRepository invoiceRepository;

    public CustomerInvoiceService(CustomerInvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public List<CustomerInvoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public CustomerInvoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CustomerInvoice", "id", id));
    }

    public CustomerInvoice createInvoice(CustomerInvoice invoice) {
        if (invoice.getIssueDate() == null) invoice.setIssueDate(LocalDateTime.now());
        if (invoice.getDueDate() == null) invoice.setDueDate(LocalDateTime.now().plusDays(30));
        if (invoice.getStatus() == null) invoice.setStatus("ISSUED");
        return invoiceRepository.save(invoice);
    }

    public CustomerInvoice updateInvoice(Long id, CustomerInvoice details) {
        CustomerInvoice invoice = getInvoiceById(id);
        if (details.getInvoiceNumber() != null) invoice.setInvoiceNumber(details.getInvoiceNumber());
        if (details.getDueDate() != null) invoice.setDueDate(details.getDueDate());
        if (details.getTotalAmount() != null) invoice.setTotalAmount(details.getTotalAmount());
        if (details.getStatus() != null) invoice.setStatus(details.getStatus());
        if (details.getCustomerName() != null) invoice.setCustomerName(details.getCustomerName());
        if (details.getCustomerEmail() != null) invoice.setCustomerEmail(details.getCustomerEmail());
        return invoiceRepository.save(invoice);
    }

    public void deleteInvoice(Long id) {
        CustomerInvoice invoice = getInvoiceById(id);
        invoiceRepository.delete(invoice);
    }
}
