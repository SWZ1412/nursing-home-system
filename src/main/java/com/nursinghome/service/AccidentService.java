package com.nursinghome.service;

import com.nursinghome.entity.AccidentRecord;
import java.util.List;

public interface AccidentService {
    List<AccidentRecord> findAll();
    AccidentRecord findById(Integer id);
    boolean add(AccidentRecord record);
    boolean update(AccidentRecord record);
    String generateAccidentNo();
}