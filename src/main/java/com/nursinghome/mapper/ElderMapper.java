package com.nursinghome.mapper;

import com.nursinghome.entity.Elder;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ElderMapper {

    @Select("SELECT e.*, r.room_no FROM elder e LEFT JOIN room r ON e.room_id = r.id WHERE e.status = 'ACTIVE' ORDER BY e.id DESC")
    List<Elder> findAll();

    @Select("SELECT * FROM elder WHERE id = #{id}")
    Elder findById(@Param("id") Integer id);

    @Select("SELECT * FROM elder WHERE elder_no = #{elderNo}")
    Elder findByElderNo(@Param("elderNo") String elderNo);

    @Select("SELECT * FROM elder WHERE name LIKE CONCAT('%', #{name}, '%') AND status = 'ACTIVE'")
    List<Elder> findByName(@Param("name") String name);

    @Insert("INSERT INTO elder (elder_no, name, gender, birthday, id_card, phone, " +
            "emergency_contact, emergency_phone, address, admission_date, health_status, " +
            "room_id, bed_no, status) VALUES (#{elderNo}, #{name}, #{gender}, #{birthday}, " +
            "#{idCard}, #{phone}, #{emergencyContact}, #{emergencyPhone}, #{address}, " +
            "#{admissionDate}, #{healthStatus}, #{roomId}, #{bedNo}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Elder elder);

    @Update("UPDATE elder SET name=#{name}, gender=#{gender}, birthday=#{birthday}, " +
            "id_card=#{idCard}, phone=#{phone}, emergency_contact=#{emergencyContact}, " +
            "emergency_phone=#{emergencyPhone}, address=#{address}, " +
            "health_status=#{healthStatus}, room_id=#{roomId}, bed_no=#{bedNo}, " +
            "status=#{status} WHERE id=#{id}")
    int update(Elder elder);

    @Delete("DELETE FROM elder WHERE id = #{id}")
    int deleteById(@Param("id") Integer id);

    @Select("SELECT COUNT(*) FROM elder WHERE status = 'ACTIVE'")
    int countActive();

    @Select("SELECT COUNT(*) FROM elder")
    int countAll();

    @Update("UPDATE elder SET status = 'ACTIVE' WHERE status IS NULL OR status = ''")
    int fixNullStatus();
}