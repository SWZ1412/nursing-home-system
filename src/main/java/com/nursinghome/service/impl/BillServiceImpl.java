package com.nursinghome.service.impl;

import com.nursinghome.entity.Bill;
import com.nursinghome.entity.Payment;
import com.nursinghome.mapper.BillMapper;
import com.nursinghome.mapper.PaymentMapper;
import com.nursinghome.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private PaymentMapper paymentMapper;

    @Override
    public List<Bill> findAll() {
        return billMapper.findAll();
    }

    @Override
    public List<Bill> findByElderId(Integer elderId) {
        return billMapper.findByElderId(elderId);
    }

    @Override
    public List<Bill> findOverdueBills() {
        return billMapper.findOverdueBills();
    }

    @Override
    public boolean generateBill(Bill bill) {
        bill.setBillNo(generateBillNo());
        bill.setPaidAmount(BigDecimal.ZERO);
        bill.setStatus("UNPAID");
        return billMapper.insert(bill) > 0;
    }

    @Override
    @Transactional
    public boolean payBill(Payment payment) {
        // 检查账单是否存在
        Bill bill = billMapper.findById(payment.getBillId());
        if (bill == null) {
            throw new IllegalArgumentException("账单不存在");
        }
        // 已缴清的账单不允许再缴
        if ("PAID".equals(bill.getStatus())) {
            throw new IllegalArgumentException("该账单已缴清，无需再缴");
        }
        // 检查是否超额支付
        BigDecimal remaining = bill.getAmount().subtract(bill.getPaidAmount());
        if (payment.getAmount().compareTo(remaining) > 0) {
            throw new IllegalArgumentException("缴费金额超过待缴金额（待缴：¥" + remaining + "）");
        }

        payment.setPaymentNo(generatePaymentNo());
        payment.setPaymentTime(new Date());
        payment.setElderId(bill.getElderId());
        int result = paymentMapper.insert(payment);
        if (result > 0) {
            BigDecimal newPaid = bill.getPaidAmount().add(payment.getAmount());
            if (newPaid.compareTo(bill.getAmount()) >= 0) {
                bill.setStatus("PAID");
                bill.setPaidAmount(bill.getAmount());
            } else {
                bill.setStatus("PARTIAL");
                bill.setPaidAmount(newPaid);
            }
            billMapper.update(bill);
        }
        return result > 0;
    }

    @Override
    @Transactional
    public boolean deleteBill(Integer id) {
        // 先删除关联的缴费记录（否则外键约束会阻止删除账单）
        paymentMapper.deleteByBillId(id);
        return billMapper.deleteById(id) > 0;
    }

    @Override
    public BigDecimal getUnpaidTotal(Integer elderId) {
        return billMapper.getUnpaidTotal(elderId);
    }

    @Override
    public String generateBillNo() {
        return "BILL" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    @Override
    public String generatePaymentNo() {
        return "PAY" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }
}