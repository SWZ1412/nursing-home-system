package com.nursinghome.service.impl;

import com.nursinghome.entity.MedicationPlan;
import com.nursinghome.mapper.MedicationPlanMapper;
import com.nursinghome.service.MedicationPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationPlanServiceImpl implements MedicationPlanService {

    @Autowired
    private MedicationPlanMapper medicationPlanMapper;

    @Override
    public List<MedicationPlan> findAll() {
        return medicationPlanMapper.findAll();
    }

    @Override
    public MedicationPlan findById(Integer id) {
        return medicationPlanMapper.findById(id);
    }

    @Override
    public List<MedicationPlan> findByElderId(Integer elderId) {
        return medicationPlanMapper.findByElderId(elderId);
    }

    @Override
    public List<MedicationPlan> findByStatus(String status) {
        return medicationPlanMapper.findByStatus(status);
    }

    @Override
    public boolean add(MedicationPlan medicationPlan) {
        medicationPlan.setStatus("ACTIVE");
        return medicationPlanMapper.insert(medicationPlan) > 0;
    }

    @Override
    public boolean update(MedicationPlan medicationPlan) {
        return medicationPlanMapper.update(medicationPlan) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return medicationPlanMapper.deleteById(id) > 0;
    }

    @Override
    public boolean updateStatus(Integer id, String status) {
        return medicationPlanMapper.updateStatus(id, status) > 0;
    }
}
