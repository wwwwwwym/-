package com.example.wms_springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Entity;
import java.util.Date;

/**
 * 出入库记录信息
 */
@Data
@TableName("deposityrecord")
public class deposityRecord {


    @TableId(type = IdType.AUTO)
    private Integer recordId;//记录id
    private String pname;//产品名称
    private Integer deposityId;//仓库id
    private Integer type;//出入库类型
    private Integer quantity;//产品数量
    private Double price;//产品价格
    private Integer state;//当前状态
    private Integer applyId;//申请人id
    private String applyRemark;//申请备注
    private Date applyTime;//申请时间
    private Integer reviewId;//审核人id
    private String reviewRemark;//审核备注
    private Date reviewTime;//审核时间

}
