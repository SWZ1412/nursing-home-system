package com.nursinghome.entity;

import lombok.Data;
import java.util.Date;

@Data
public class AccidentRecord {
    private Integer id;
    private String accidentNo;
    private Integer elderId;
    private Integer employeeId;
    private String accidentType;
    private Date accidentTime;
    private String location;
    private String description;
    private String severity;
    private String handlingResult;
    private Integer reporterId;
    private Date reportTime;
    private String status;

    // 关联字段
    private String elderName;
    private String employeeName;
}