package com.nursinghome.service;

import com.nursinghome.entity.MedicationPlan;
import java.util.List;

public interface MedicationPlanService {
    List<MedicationPlan> findAll();
    MedicationPlan findById(Integer id);
    List<MedicationPlan> findByElderId(Integer elderId);
    List<MedicationPlan> findByStatus(String status);
    boolean add(MedicationPlan medicationPlan);
    boolean update(MedicationPlan medicationPlan);
    boolean delete(Integer id);
    boolean updateStatus(Integer id, String status);
}
