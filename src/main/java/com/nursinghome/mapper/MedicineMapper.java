package com.nursinghome.mapper;

import com.nursinghome.entity.Medicine;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MedicineMapper {

    @Select("SELECT * FROM medicine WHERE status = 1 ORDER BY id DESC")
    List<Medicine> findAll();

    @Select("SELECT * FROM medicine WHERE id = #{id}")
    Medicine findById(@Param("id") Integer id);

    @Select("SELECT * FROM medicine WHERE name LIKE CONCAT('%', #{name}, '%') AND status = 1")
    List<Medicine> findByName(@Param("name") String name);

    @Select("SELECT * FROM medicine WHERE stock < min_stock AND status = 1")
    List<Medicine> findLowStock();

    @Insert("INSERT INTO medicine (medicine_no, name, category, specification, unit, price, " +
            "stock, min_stock, manufacturer, expiry_date, description, status) " +
            "VALUES (#{medicineNo}, #{name}, #{category}, #{specification}, #{unit}, #{price}, " +
            "#{stock}, #{minStock}, #{manufacturer}, #{expiryDate}, #{description}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Medicine medicine);

    @Update("UPDATE medicine SET name=#{name}, category=#{category}, specification=#{specification}, " +
            "unit=#{unit}, price=#{price}, stock=#{stock}, min_stock=#{minStock}, " +
            "manufacturer=#{manufacturer}, expiry_date=#{expiryDate}, description=#{description}, " +
            "status=#{status} WHERE id=#{id}")
    int update(Medicine medicine);

    @Update("UPDATE medicine SET stock = stock - #{quantity} WHERE id = #{id}")
    int reduceStock(@Param("id") Integer id, @Param("quantity") Integer quantity);

    @Update("UPDATE medicine SET stock = stock + #{quantity} WHERE id = #{id}")
    int increaseStock(@Param("id") Integer id, @Param("quantity") Integer quantity);

    @Delete("DELETE FROM medicine WHERE id = #{id}")
    int deleteById(@Param("id") Integer id);
}