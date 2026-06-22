package com.nursinghome.mapper;

import com.nursinghome.entity.Room;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RoomMapper {

    @Select("SELECT * FROM room ORDER BY building, floor, room_no")
    List<Room> findAll();

    @Select("SELECT * FROM room WHERE id = #{id}")
    Room findById(@Param("id") Integer id);

    @Select("SELECT * FROM room WHERE status = 'AVAILABLE'")
    List<Room> findAvailableRooms();

    @Insert("INSERT INTO room (room_no, building, floor, room_type, capacity, occupied, price, status, description) " +
            "VALUES (#{roomNo}, #{building}, #{floor}, #{roomType}, #{capacity}, #{occupied}, #{price}, #{status}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Room room);

    @Update("UPDATE room SET room_no=#{roomNo}, building=#{building}, floor=#{floor}, " +
            "room_type=#{roomType}, capacity=#{capacity}, occupied=#{occupied}, " +
            "price=#{price}, status=#{status}, description=#{description} WHERE id=#{id}")
    int update(Room room);

    @Update("UPDATE room SET occupied = occupied + 1 WHERE id = #{roomId}")
    int incrementOccupied(@Param("roomId") Integer roomId);

    @Update("UPDATE room SET occupied = GREATEST(occupied - 1, 0) WHERE id = #{roomId}")
    int decrementOccupied(@Param("roomId") Integer roomId);

    // 修复NULL和负数：将所有NULL或负数的occupied重置为0
    @Update("UPDATE room SET occupied = 0 WHERE occupied IS NULL OR occupied < 0")
    int fixInvalidOccupied();

    // 根据实际老人入住情况重新计算每个房间的occupied
    @Update("UPDATE room r SET r.occupied = (" +
            "SELECT COALESCE(COUNT(*), 0) FROM elder e " +
            "WHERE e.room_id = r.id AND e.status = 'ACTIVE'" +
            ")")
    int recalculateOccupied();

    @Update("UPDATE room SET status = #{status} WHERE id = #{id}")
    int updateStatus(@Param("id") Integer id, @Param("status") String status);

    @Delete("DELETE FROM room WHERE id = #{id}")
    int deleteById(@Param("id") Integer id);

    @Select("SELECT COUNT(*) FROM room WHERE status = 'AVAILABLE'")
    int countAvailable();

    @Select("SELECT COALESCE(SUM(capacity), 0) FROM room")
    int sumCapacity();
}