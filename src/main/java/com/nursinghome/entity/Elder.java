package com.nursinghome.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Elder {
    private Integer id;
    private String elderNo;
    private String name;
    private String gender;
    private Date birthday;
    private String idCard;
    private String phone;
    private String emergencyContact;
    private String emergencyPhone;
    private String address;
    private Date admissionDate;
    private String healthStatus;
    private Integer roomId;
    private String bedNo;
    private String status;
    private Date createTime;
    private Date updateTime;

    // 关联显示字段（非数据库列）
    private String roomNo;
}