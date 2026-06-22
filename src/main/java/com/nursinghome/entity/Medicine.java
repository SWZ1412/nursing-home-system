package com.nursinghome.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Medicine {
    private Integer id;
    private String medicineNo;
    private String name;
    private String category;
    private String specification;
    private String unit;
    private BigDecimal price;
    private Integer stock;
    private Integer minStock;
    private String manufacturer;
    private Date expiryDate;
    private String description;
    private Integer status;
    private Date createTime;
    private Date updateTime;
}