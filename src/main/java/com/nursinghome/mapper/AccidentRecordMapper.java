package com.nursinghome.mapper;
import com.nursinghome.entity.AccidentRecord;
import org.apache.ibatis.annotations.*;
import java.util.List;
@Mapper
public interface AccidentRecordMapper {
    @Select("SELECT a.*, e.name as elder_name FROM accident_record a LEFT JOIN elder e ON a.elder_id=e.id ORDER BY a.accident_time DESC")
    List<AccidentRecord> findAll();
    @Insert("INSERT INTO accident_record(accident_no,elder_id,accident_type,accident_time,location,description,severity,handling_result,reporter_id,status) " +
            "VALUES(#{accidentNo},#{elderId},#{accidentType},#{accidentTime},#{location},#{description},#{severity},#{handlingResult},#{reporterId},#{status})")
    int insert(AccidentRecord record);
    @Update("UPDATE accident_record SET handling_result=#{handlingResult}, status=#{status} WHERE id=#{id}")
    int update(AccidentRecord record);
    @Delete("DELETE FROM accident_record WHERE id=#{id}")
    int deleteById(@Param("id") Integer id);

    @Select("SELECT a.*, e.name as elder_name FROM accident_record a LEFT JOIN elder e ON a.elder_id = e.id WHERE a.id = #{id}")
    AccidentRecord findById(@Param("id") Integer id);
}