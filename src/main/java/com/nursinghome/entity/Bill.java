package com.nursinghome.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Bill {
    private Integer id;
    private String billNo;
    private Integer elderId;
    private String billType;
    private BigDecimal amount;
    private BigDecimal paidAmount;
    private String status;
    private String billMonth;
    private Date dueDate;
    private String remark;
    private Date createTime;
    private Date updateTime;

    // 关联字段
    private String elderName;
}