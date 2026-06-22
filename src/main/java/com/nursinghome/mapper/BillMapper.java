package com.nursinghome.mapper;

import com.nursinghome.entity.Bill;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface BillMapper {

    @Select("SELECT b.*, e.name as elder_name FROM bill b " +
            "LEFT JOIN elder e ON b.elder_id = e.id ORDER BY b.create_time DESC")
    List<Bill> findAll();

    @Select("SELECT b.*, e.name as elder_name FROM bill b " +
            "LEFT JOIN elder e ON b.elder_id = e.id WHERE b.elder_id = #{elderId}")
    List<Bill> findByElderId(@Param("elderId") Integer elderId);

    @Select("SELECT * FROM bill WHERE status = 'UNPAID' AND due_date < NOW()")
    List<Bill> findOverdueBills();

    @Insert("INSERT INTO bill (bill_no, elder_id, bill_type, amount, paid_amount, status, bill_month, due_date, remark) " +
            "VALUES (#{billNo}, #{elderId}, #{billType}, #{amount}, #{paidAmount}, #{status}, #{billMonth}, #{dueDate}, #{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Bill bill);

    @Update("UPDATE bill SET status=#{status}, paid_amount=#{paidAmount} WHERE id=#{id}")
    int update(Bill bill);

    @Delete("DELETE FROM bill WHERE id = #{id}")
    int deleteById(@Param("id") Integer id);

    @Select("SELECT COALESCE(SUM(amount), 0) FROM bill WHERE status != 'PAID' AND elder_id = #{elderId}")
    BigDecimal getUnpaidTotal(@Param("elderId") Integer elderId);

    @Select("SELECT b.*, e.name as elder_name FROM bill b LEFT JOIN elder e ON b.elder_id = e.id WHERE b.id = #{billId}")
    Bill findById(@Param("billId") Integer billId);
}