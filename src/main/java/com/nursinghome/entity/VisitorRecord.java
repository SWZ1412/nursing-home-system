package com.nursinghome.entity;

import lombok.Data;
import java.util.Date;

@Data
public class VisitorRecord {
    private Integer id;
    private String visitorName;
    private String idCard;
    private String phone;
    private Integer elderId;
    private Date visitTime;
    private Date leaveTime;
    private String purpose;
    private String remark;
    private Integer operatorId;
    private Date createTime;

    // 关联字段
    private String elderName;
}