package com.nursinghome.service;

import com.nursinghome.entity.Room;
import java.util.List;

public interface RoomService {
    List<Room> findAll();
    Room findById(Integer id);
    List<Room> findAvailableRooms();
    boolean add(Room room);
    boolean update(Room room);
    boolean delete(Integer id);
    int countAvailable();
    int sumCapacity();
}