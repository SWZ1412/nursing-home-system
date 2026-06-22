package com.nursinghome.service;

import com.nursinghome.entity.Elder;
import java.util.List;

public interface ElderService {
    List<Elder> findAll();
    Elder findById(Integer id);
    Elder findByElderNo(String elderNo);
    List<Elder> findByName(String name);
    boolean add(Elder elder);
    boolean update(Elder elder);
    boolean delete(Integer id);
    int countActive();
    String generateElderNo();
}