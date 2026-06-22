package com.nursinghome.service.impl;

import com.nursinghome.entity.Elder;
import com.nursinghome.entity.Room;
import com.nursinghome.mapper.ElderMapper;
import com.nursinghome.mapper.RoomMapper;
import com.nursinghome.service.ElderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ElderServiceImpl implements ElderService {

    @Autowired
    private ElderMapper elderMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Override
    public List<Elder> findAll() {
        return elderMapper.findAll();
    }

    @Override
    public Elder findById(Integer id) {
        return elderMapper.findById(id);
    }

    @Override
    public Elder findByElderNo(String elderNo) {
        return elderMapper.findByElderNo(elderNo);
    }

    @Override
    public List<Elder> findByName(String name) {
        return elderMapper.findByName(name);
    }

    @Override
    @Transactional
    public boolean add(Elder elder) {
        elder.setElderNo(generateElderNo());
        elder.setStatus("ACTIVE");
        elder.setAdmissionDate(new Date());
        int result = elderMapper.insert(elder);
        if (result > 0 && elder.getRoomId() != null) {
            roomMapper.incrementOccupied(elder.getRoomId());
            updateRoomStatus(elder.getRoomId()); // 自动更新房间状态
        }
        return result > 0;
    }

    @Override
    @Transactional
    public boolean update(Elder elder) {
        // 如果前端没有传status，保留原来的状态（防止编辑时意外清空）
        if (elder.getStatus() == null) {
            Elder existing = elderMapper.findById(elder.getId());
            if (existing != null) {
                elder.setStatus(existing.getStatus());
            }
        }
        // 处理房间变更：旧房间减人，新房间加人
        Elder oldElder = elderMapper.findById(elder.getId());
        if (oldElder != null) {
            Integer oldRoomId = oldElder.getRoomId();
            Integer newRoomId = elder.getRoomId();
            if (oldRoomId != null && !oldRoomId.equals(newRoomId)) {
                roomMapper.decrementOccupied(oldRoomId);
                updateRoomStatus(oldRoomId); // 旧房间状态自动更新
            }
            if (newRoomId != null && !newRoomId.equals(oldRoomId)) {
                roomMapper.incrementOccupied(newRoomId);
                updateRoomStatus(newRoomId); // 新房间状态自动更新
            }
        }
        return elderMapper.update(elder) > 0;
    }

    @Override
    @Transactional
    public boolean delete(Integer id) {
        Elder elder = elderMapper.findById(id);
        if (elder != null && elder.getRoomId() != null) {
            roomMapper.decrementOccupied(elder.getRoomId());
            updateRoomStatus(elder.getRoomId()); // 房间状态自动更新
        }
        return elderMapper.deleteById(id) > 0;
    }

    /**
     * 根据实际入住情况自动更新房间状态
     */
    private void updateRoomStatus(Integer roomId) {
        Room room = roomMapper.findById(roomId);
        if (room != null && !"MAINTENANCE".equals(room.getStatus())) {
            if (room.getOccupied() >= room.getCapacity()) {
                roomMapper.updateStatus(roomId, "FULL");
            } else if (room.getOccupied() == 0) {
                roomMapper.updateStatus(roomId, "AVAILABLE");
            } else {
                roomMapper.updateStatus(roomId, "AVAILABLE"); // 有入住但未满
            }
        }
    }

    @Override
    public int countActive() {
        return elderMapper.countActive();
    }

    @Override
    public String generateElderNo() {
        String prefix = "EL" + new SimpleDateFormat("yyyyMMdd").format(new Date());
        // 统计所有老人（不只是ACTIVE），避免编号重复
        int count = elderMapper.countAll() + 1;
        return prefix + String.format("%04d", count);
    }

    /**
     * 修复数据库中状态为NULL的老人记录
     */
    public int fixNullStatus() {
        return elderMapper.fixNullStatus();
    }
}