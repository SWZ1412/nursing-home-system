package com.nursinghome.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Room {
    private Integer id;
    private String roomNo;
    private String building;
    private Integer floor;
    private String roomType;
    private Integer capacity;
    private Integer occupied;
    private Double price;
    private String status;
    private String description;
    private Date createTime;
    private Date updateTime;
}
