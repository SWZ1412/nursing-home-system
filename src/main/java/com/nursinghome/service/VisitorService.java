package com.nursinghome.service;

import com.nursinghome.entity.VisitorRecord;

import java.util.Date;
import java.util.List;

public interface VisitorService {
    List<VisitorRecord> findAll();
    List<VisitorRecord> findByVisitorName(String name);
    boolean add(VisitorRecord record);
    boolean leave(Integer id, String leaveTime);

    boolean leave(Integer id, Date leaveTime);
}