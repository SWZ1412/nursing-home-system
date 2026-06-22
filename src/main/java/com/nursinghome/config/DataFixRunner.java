package com.nursinghome.config;

import com.nursinghome.entity.Room;
import com.nursinghome.mapper.ElderMapper;
import com.nursinghome.mapper.RoomMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 启动时自动修复历史数据问题
 */
@Component
public class DataFixRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataFixRunner.class);

    @Autowired
    private ElderMapper elderMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Override
    public void run(String... args) {
        try {
            // 1. 修复老人status为NULL的记录
            int fixedElders = elderMapper.fixNullStatus();
            if (fixedElders > 0) {
                log.info("已修复 {} 条status为NULL的老人记录，已重置为ACTIVE", fixedElders);
            }

            // 2. 修复房间occupied为NULL或负数的记录
            int fixedRooms = roomMapper.fixInvalidOccupied();
            if (fixedRooms > 0) {
                log.info("已修复 {} 条房间occupied为NULL或负数的记录，已重置为0", fixedRooms);
            }

            // 3. 根据实际老人入住情况重新计算所有房间的occupied
            int recalculated = roomMapper.recalculateOccupied();
            log.info("已根据实际入住情况重新计算 {} 条房间的入住人数", recalculated);

            // 4. 更新房间状态（根据实际入住情况）
            int statusUpdated = updateAllRoomStatus();
            if (statusUpdated > 0) {
                log.info("已更新房间状态");
            }
        } catch (Exception e) {
            log.warn("数据修复时出现异常（可能表还不存在，可忽略）: {}", e.getMessage());
        }
    }

    private int updateAllRoomStatus() {
        List<Room> rooms = roomMapper.findAll();
        int count = 0;
        for (Room room : rooms) {
            if (!"MAINTENANCE".equals(room.getStatus())) {
                String newStatus;
                if (room.getOccupied() != null && room.getOccupied() >= room.getCapacity()) {
                    newStatus = "FULL";
                } else {
                    newStatus = "AVAILABLE";
                }
                if (!newStatus.equals(room.getStatus())) {
                    roomMapper.updateStatus(room.getId(), newStatus);
                    count++;
                }
            }
        }
        return count;
    }
}
