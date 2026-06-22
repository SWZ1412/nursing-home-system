package com.nursinghome.mapper;

import com.nursinghome.entity.MedicationPlan;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MedicationPlanMapper {

    @Select("SELECT mp.*, e.name as elder_name, m.name as medicine_name, m.specification as medicine_specification " +
            "FROM medication_plan mp LEFT JOIN elder e ON mp.elder_id = e.id " +
            "LEFT JOIN medicine m ON mp.medicine_id = m.id ORDER BY mp.create_time DESC")
    List<MedicationPlan> findAll();

    @Select("SELECT mp.*, e.name as elder_name, m.name as medicine_name, m.specification as medicine_specification " +
            "FROM medication_plan mp LEFT JOIN elder e ON mp.elder_id = e.id " +
            "LEFT JOIN medicine m ON mp.medicine_id = m.id WHERE mp.id = #{id}")
    MedicationPlan findById(@Param("id") Integer id);

    @Select("SELECT mp.*, e.name as elder_name, m.name as medicine_name, m.specification as medicine_specification " +
            "FROM medication_plan mp LEFT JOIN elder e ON mp.elder_id = e.id " +
            "LEFT JOIN medicine m ON mp.medicine_id = m.id WHERE mp.elder_id = #{elderId} ORDER BY mp.create_time DESC")
    List<MedicationPlan> findByElderId(@Param("elderId") Integer elderId);

    @Select("SELECT mp.*, e.name as elder_name, m.name as medicine_name, m.specification as medicine_specification " +
            "FROM medication_plan mp LEFT JOIN elder e ON mp.elder_id = e.id " +
            "LEFT JOIN medicine m ON mp.medicine_id = m.id WHERE mp.status = #{status} ORDER BY mp.create_time DESC")
    List<MedicationPlan> findByStatus(@Param("status") String status);

    @Insert("INSERT INTO medication_plan (elder_id, medicine_id, dosage, frequency, start_date, end_date, remark, status, doctor_id) " +
            "VALUES (#{elderId}, #{medicineId}, #{dosage}, #{frequency}, #{startDate}, #{endDate}, #{remark}, #{status}, #{doctorId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(MedicationPlan medicationPlan);

    @Update("UPDATE medication_plan SET dosage=#{dosage}, frequency=#{frequency}, " +
            "start_date=#{startDate}, end_date=#{endDate}, remark=#{remark}, status=#{status} WHERE id=#{id}")
    int update(MedicationPlan medicationPlan);

    @Update("UPDATE medication_plan SET status = #{status} WHERE id = #{id}")
    int updateStatus(@Param("id") Integer id, @Param("status") String status);

    @Delete("DELETE FROM medication_plan WHERE id = #{id}")
    int deleteById(@Param("id") Integer id);
}
