package com.nursinghome.mapper;

import com.nursinghome.entity.VisitorRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VisitorMapperRecord {

    @Select("SELECT v.*, e.name as elder_name FROM visitor_record v " +
            "LEFT JOIN elder e ON v.elder_id = e.id ORDER BY v.visit_time DESC")
    List<VisitorRecord> findAll();

    @Select("SELECT v.*, e.name as elder_name FROM visitor_record v " +
            "LEFT JOIN elder e ON v.elder_id = e.id WHERE v.visitor_name LIKE CONCAT('%', #{name}, '%')")
    List<VisitorRecord> findByVisitorName(@Param("name") String name);

    @Insert("INSERT INTO visitor_record (visitor_name, id_card, phone, elder_id, visit_time, purpose, remark, operator_id) " +
            "VALUES (#{visitorName}, #{idCard}, #{phone}, #{elderId}, #{visitTime}, #{purpose}, #{remark}, #{operatorId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(VisitorRecord visitorRecord);

    @Update("UPDATE visitor_record SET leave_time = #{leaveTime} WHERE id = #{id}")
    int updateLeaveTime(@Param("id") Integer id, @Param("leaveTime") java.util.Date leaveTime);
}