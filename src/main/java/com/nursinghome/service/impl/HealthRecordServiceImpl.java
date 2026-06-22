package com.nursinghome.service.impl;

import com.nursinghome.entity.HealthRecord;
import com.nursinghome.mapper.HealthRecordMapper;
import com.nursinghome.service.HealthRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HealthRecordServiceImpl implements HealthRecordService {

    @Autowired
    private HealthRecordMapper healthRecordMapper;

    @Override
    public List<HealthRecord> findAll() {
        return healthRecordMapper.findAll();
    }

    @Override
    public List<HealthRecord> findByElderId(Integer elderId) {
        return healthRecordMapper.findByElderId(elderId);
    }

    @Override
    public boolean add(HealthRecord healthRecord) {
        return healthRecordMapper.insert(healthRecord) > 0;
    }

    @Override
    public boolean update(HealthRecord healthRecord) {
        return healthRecordMapper.update(healthRecord) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return healthRecordMapper.deleteById(id) > 0;
    }
}