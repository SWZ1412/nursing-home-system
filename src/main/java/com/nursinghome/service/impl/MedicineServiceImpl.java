package com.nursinghome.service.impl;

import com.nursinghome.entity.Medicine;
import com.nursinghome.mapper.MedicineMapper;
import com.nursinghome.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class MedicineServiceImpl implements MedicineService {

    @Autowired
    private MedicineMapper medicineMapper;

    @Override
    public List<Medicine> findAll() {
        return medicineMapper.findAll();
    }

    @Override
    public Medicine findById(Integer id) {
        return medicineMapper.findById(id);
    }

    @Override
    public List<Medicine> findByName(String name) {
        return medicineMapper.findByName(name);
    }

    @Override
    public List<Medicine> findLowStock() {
        return medicineMapper.findLowStock();
    }

    @Override
    public boolean add(Medicine medicine) {
        medicine.setMedicineNo(generateMedicineNo());
        medicine.setStatus(1);
        return medicineMapper.insert(medicine) > 0;
    }

    @Override
    public boolean update(Medicine medicine) {
        return medicineMapper.update(medicine) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return medicineMapper.deleteById(id) > 0;
    }

    @Override
    public boolean reduceStock(Integer id, Integer quantity) {
        return medicineMapper.reduceStock(id, quantity) > 0;
    }

    @Override
    public String generateMedicineNo() {
        String prefix = "MED" + new SimpleDateFormat("yyyyMMdd").format(new Date());
        List<Medicine> list = medicineMapper.findAll();
        int count = list.size() + 1;
        return prefix + String.format("%04d", count);
    }
}