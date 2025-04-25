package com.example.wms_springboot.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Entity;


/**
 * 角色类
 */
@Data
@TableName("role")
public class Role {

    @TableId(type = IdType.NONE)
    private Integer roleid;//角色id（1是普通用户，1是管理员，2是超管）
    private String rolename;//角色名称
}
