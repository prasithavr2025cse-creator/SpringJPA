package com.uam.hub.service;

import com.uam.hub.entity.Payment;
import com.uam.hub.exception.ResourceNotFoundException;
import com.uam.hub.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", id));
    }

    public Payment createPayment(Payment payment) {
        if (payment.getPaymentDate() == null) payment.setPaymentDate(LocalDateTime.now());
        if (payment.getPaymentMethod() == null) payment.setPaymentMethod("BANK_TRANSFER");
        if (payment.getPaymentType() == null) payment.setPaymentType("CUSTOMER_INCOME");
        return paymentRepository.save(payment);
    }

    public Payment updatePayment(Long id, Payment details) {
        Payment payment = getPaymentById(id);
        if (details.getTransactionReference() != null) payment.setTransactionReference(details.getTransactionReference());
        if (details.getAmount() != null) payment.setAmount(details.getAmount());
        if (details.getPaymentMethod() != null) payment.setPaymentMethod(details.getPaymentMethod());
        if (details.getPaymentType() != null) payment.setPaymentType(details.getPaymentType());
        return paymentRepository.save(payment);
    }

    public void deletePayment(Long id) {
        Payment payment = getPaymentById(id);
        paymentRepository.delete(payment);
    }
}
