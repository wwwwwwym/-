package com.example.wms_springboot.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 用户类
 */
@Data
@TableName("user")
public class User {

    @TableId(type = IdType.INPUT)
    private Integer userid;//用户id
    private String username;//用户名称
    private String password;//用户密码
    private Integer roleid;//角色id
    private Date time;//入职时间
    private String deposity;//负责仓库
    private String telephone;//员工电话

    @TableField(exist = false)
    private String token;

}
