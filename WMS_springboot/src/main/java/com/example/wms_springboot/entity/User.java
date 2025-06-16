package com.example.wms_springboot.entity;


import cn.hutool.core.annotation.Alias;
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


    @TableId
    @Alias("工号")
    private Integer userid;//用户id
    @Alias("用户名")
    private String username;//用户名称
    @Alias("用户密码")
    private String password;//用户密码
    @Alias("用户角色")
    private String role;//角色
    @Alias("入职时间")
    private String time;//入职时间
    @Alias("用户电话")
    private String telephone;//员工电话
    @Alias("是否注册")
    private Boolean isregister;//员工电话



    @TableField(exist = false)
    private String token;
}
