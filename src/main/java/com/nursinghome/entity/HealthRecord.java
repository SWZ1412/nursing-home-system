package com.nursinghome.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class HealthRecord {
    private Integer id;
    private Integer elderId;
    private Date recordDate;
    private BigDecimal temperature;
    private Integer bloodPressureSystolic;
    private Integer bloodPressureDiastolic;
    private BigDecimal bloodSugar;
    private Integer heartRate;
    private String medication;
    private String nurseNote;
    private Integer nurseId;
    private Date createTime;

    private String elderName;
}