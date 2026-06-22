package com.nursinghome.mapper;

import com.nursinghome.entity.HealthRecord;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface HealthRecordMapper {

    @Select("SELECT h.*, e.name as elder_name FROM health_record h " +
            "LEFT JOIN elder e ON h.elder_id = e.id ORDER BY h.record_date DESC")
    List<HealthRecord> findAll();

    @Select("SELECT h.*, e.name as elder_name FROM health_record h " +
            "LEFT JOIN elder e ON h.elder_id = e.id WHERE h.elder_id = #{elderId} " +
            "ORDER BY h.record_date DESC")
    List<HealthRecord> findByElderId(@Param("elderId") Integer elderId);

    @Select("SELECT h.*, e.name as elder_name FROM health_record h " +
            "LEFT JOIN elder e ON h.elder_id = e.id WHERE h.record_date BETWEEN #{startDate} AND #{endDate}")
    List<HealthRecord> findByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Insert("INSERT INTO health_record (elder_id, record_date, temperature, blood_pressure_systolic, " +
            "blood_pressure_diastolic, blood_sugar, heart_rate, medication, nurse_note, nurse_id) " +
            "VALUES (#{elderId}, #{recordDate}, #{temperature}, #{bloodPressureSystolic}, " +
            "#{bloodPressureDiastolic}, #{bloodSugar}, #{heartRate}, #{medication}, #{nurseNote}, #{nurseId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(HealthRecord healthRecord);

    @Update("UPDATE health_record SET temperature=#{temperature}, " +
            "blood_pressure_systolic=#{bloodPressureSystolic}, " +
            "blood_pressure_diastolic=#{bloodPressureDiastolic}, blood_sugar=#{bloodSugar}, " +
            "heart_rate=#{heartRate}, medication=#{medication}, nurse_note=#{nurseNote} WHERE id=#{id}")
    int update(HealthRecord healthRecord);

    @Delete("DELETE FROM health_record WHERE id = #{id}")
    int deleteById(@Param("id") Integer id);
}