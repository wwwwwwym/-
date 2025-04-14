package com.example.wms_springboot.entity;


import lombok.Data;

import javax.persistence.Entity;


//角色类
@Data
@Entity
public class Role {

    private Integer roleid;//角色id（1是普通用户，1是管理员，2是超管）
    private String rolename;//角色名称
}
