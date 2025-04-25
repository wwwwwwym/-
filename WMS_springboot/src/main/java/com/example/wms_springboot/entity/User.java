package com.example.wms_springboot.entity;


import lombok.Data;

import javax.persistence.Entity;
import java.util.Date;

//用户类
@Data
@Entity
public class User {

    private Integer userid;//用户id
    private String username;//用户名称
    private String password;//用户密码
    private Integer roleid;//角色id
    private Date time;//入职时间
    private String deposity;//负责仓库
    private String telephone;//员工电话

    private String token;

}
