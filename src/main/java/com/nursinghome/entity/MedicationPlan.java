package com.nursinghome.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class MedicationPlan {
    private Integer id;
    private Integer elderId;
    private Integer medicineId;
    private String dosage;
    private String frequency;
    private Date startDate;
    private Date endDate;
    private String remark;
    private String status;
    private Integer doctorId;
    private Date createTime;

    // 关联字段
    private String elderName;
    private String medicineName;
    private String medicineSpecification;
}