package com.nursinghome.service;

import com.nursinghome.entity.Medicine;
import java.util.List;

public interface MedicineService {
    List<Medicine> findAll();
    Medicine findById(Integer id);
    List<Medicine> findByName(String name);
    List<Medicine> findLowStock();
    boolean add(Medicine medicine);
    boolean update(Medicine medicine);
    boolean delete(Integer id);
    boolean reduceStock(Integer id, Integer quantity);
    String generateMedicineNo();
}