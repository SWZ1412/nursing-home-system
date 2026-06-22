package com.nursinghome.service;

import com.nursinghome.entity.HealthRecord;
import java.util.List;

public interface HealthRecordService {
    List<HealthRecord> findAll();
    List<HealthRecord> findByElderId(Integer elderId);
    boolean add(HealthRecord healthRecord);
    boolean update(HealthRecord healthRecord);
    boolean delete(Integer id);
}