package com.nursinghome.service.impl;

import com.nursinghome.entity.AccidentRecord;
import com.nursinghome.mapper.AccidentRecordMapper;
import com.nursinghome.service.AccidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class AccidentServiceImpl implements AccidentService {

    @Autowired
    private AccidentRecordMapper accidentRecordMapper;

    @Override
    public List<AccidentRecord> findAll() {
        return accidentRecordMapper.findAll();
    }

    @Override
    public AccidentRecord findById(Integer id) {
        return accidentRecordMapper.findById(id);
    }

    @Override
    public boolean add(AccidentRecord record) {
        record.setAccidentNo(generateAccidentNo());
        record.setReportTime(new Date());
        record.setStatus("REPORTED");
        return accidentRecordMapper.insert(record) > 0;
    }

    @Override
    public boolean update(AccidentRecord record) {
        return accidentRecordMapper.update(record) > 0;
    }

    @Override
    public String generateAccidentNo() {
        return "ACC" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }
}