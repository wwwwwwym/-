package com.example.wms_springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Entity;

/**
 * 库存信息
 */
@Data
@TableName("stockinfo")
public class stockInfo {
    @TableId(type = IdType.AUTO)
    private Integer id;//存储记录id
    private Integer deposityId;//所在仓库id
    private String pname;//产品名称
    private Integer quantity;//产品数量
    private Double price;//产品价格

}
