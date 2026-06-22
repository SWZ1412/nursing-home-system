package com.nursinghome.service;

import com.nursinghome.entity.Bill;
import com.nursinghome.entity.Payment;
import java.math.BigDecimal;
import java.util.List;

public interface BillService {
    List<Bill> findAll();
    List<Bill> findByElderId(Integer elderId);
    List<Bill> findOverdueBills();
    boolean generateBill(Bill bill);
    boolean payBill(Payment payment);
    boolean deleteBill(Integer id);
    BigDecimal getUnpaidTotal(Integer elderId);
    String generateBillNo();
    String generatePaymentNo();
}