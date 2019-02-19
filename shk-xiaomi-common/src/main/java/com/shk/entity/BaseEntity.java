package com.shk.entity;

import lombok.Data;

import java.sql.Timestamp;


/**
 * 封装一些相同的字段和属性
 */
@Data
public class BaseEntity {

    /*主键*/
    private Long id;
    /*创建时间*/
    private Timestamp created;
    /*更新时间*/
    private Timestamp updated;

}
