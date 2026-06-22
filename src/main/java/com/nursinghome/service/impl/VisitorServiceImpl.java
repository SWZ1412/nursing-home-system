package com.nursinghome.service.impl;

import com.nursinghome.entity.VisitorRecord;
import com.nursinghome.mapper.VisitorMapperRecord;
import com.nursinghome.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class VisitorServiceImpl implements VisitorService {

    @Autowired
    private VisitorMapperRecord visitorRecordMapper;

    @Override
    public List<VisitorRecord> findAll() {
        return visitorRecordMapper.findAll();
    }

    @Override
    public List<VisitorRecord> findByVisitorName(String name) {
        return visitorRecordMapper.findByVisitorName(name);
    }

    @Override
    public boolean add(VisitorRecord record) {
        record.setVisitTime(new Date());
        return visitorRecordMapper.insert(record) > 0;
    }

    @Override
    public boolean leave(Integer id, String leaveTime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date leaveDate = sdf.parse(leaveTime);
            return visitorRecordMapper.updateLeaveTime(id, leaveDate) > 0;
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public boolean leave(Integer id, Date leaveTime) {
        return visitorRecordMapper.updateLeaveTime(id, leaveTime) > 0;
    }
}