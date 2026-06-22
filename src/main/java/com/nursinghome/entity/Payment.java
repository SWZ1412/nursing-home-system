package com.nursinghome.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Payment {
    private Integer id;
    private String paymentNo;
    private Integer billId;
    private Integer elderId;
    private BigDecimal amount;
    private String paymentMethod;
    private Date paymentTime;
    private Integer operatorId;
    private String remark;
    private Date createTime;

    // 关联字段
    private String elderName;
    private String operatorName;
}