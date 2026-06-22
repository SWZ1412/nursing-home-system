package com.nursinghome.service.impl;

import com.nursinghome.entity.Room;
import com.nursinghome.mapper.RoomMapper;
import com.nursinghome.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomMapper roomMapper;

    @Override
    public List<Room> findAll() {
        return roomMapper.findAll();
    }

    @Override
    public Room findById(Integer id) {
        return roomMapper.findById(id);
    }

    @Override
    public List<Room> findAvailableRooms() {
        return roomMapper.findAvailableRooms();
    }

    @Override
    public boolean add(Room room) {
        room.setOccupied(0);
        room.setStatus("AVAILABLE");
        return roomMapper.insert(room) > 0;
    }

    @Override
    public boolean update(Room room) {
        // 保留原来的occupied值（编辑房间时不应覆盖入住人数）
        if (room.getOccupied() == null) {
            Room existing = roomMapper.findById(room.getId());
            if (existing != null) {
                room.setOccupied(existing.getOccupied());
            }
        }
        // 自动计算状态：只有维修是手动设置的，空闲/已满由系统根据入住情况判断
        if (!"MAINTENANCE".equals(room.getStatus())) {
            if (room.getOccupied() != null && room.getOccupied() >= room.getCapacity()) {
                room.setStatus("FULL");
            } else {
                room.setStatus("AVAILABLE");
            }
        }
        return roomMapper.update(room) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return roomMapper.deleteById(id) > 0;
    }

    @Override
    public int countAvailable() {
        return roomMapper.countAvailable();
    }

    @Override
    public int sumCapacity() {
        return roomMapper.sumCapacity();
    }
}
