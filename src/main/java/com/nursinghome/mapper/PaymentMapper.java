package com.nursinghome.mapper;

import com.nursinghome.entity.Payment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PaymentMapper {

    @Select("SELECT p.*, e.name as elder_name, u.real_name as operator_name " +
            "FROM payment p LEFT JOIN elder e ON p.elder_id = e.id " +
            "LEFT JOIN user u ON p.operator_id = u.id ORDER BY p.payment_time DESC")
    List<Payment> findAll();

    @Select("SELECT * FROM payment WHERE bill_id = #{billId}")
    List<Payment> findByBillId(@Param("billId") Integer billId);

    @Delete("DELETE FROM payment WHERE bill_id = #{billId}")
    int deleteByBillId(@Param("billId") Integer billId);

    @Insert("INSERT INTO payment (payment_no, bill_id, elder_id, amount, payment_method, payment_time, operator_id, remark) " +
            "VALUES (#{paymentNo}, #{billId}, #{elderId}, #{amount}, #{paymentMethod}, #{paymentTime}, #{operatorId}, #{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Payment payment);
}