package com.example.wms_springboot.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 入库记录信息
 */
@Data
@TableName("recordin")
public class recordIn {


    @TableId(type = IdType.AUTO )
    @TableField("record_id")
    @Alias("记录序号")
    private Integer recordId;//记录id
    @Alias("产品名称")
    private String pname;//产品名称
    @Alias("产品图片")
    private String picture;//产品照片
    @Alias("仓库id")
    private String deposity;//仓库
    @Alias("产品数量")
    private Integer quantity;//产品数量
    @Alias("产品价格")
    private Double price;//产品价格
    @Alias("当前状态")
    private Integer state;//当前状态
    @Alias("申请人工号")
    private Integer applyId;//申请人id
    @Alias("申请备注")
    private String applyRemark;//申请备注
    @Alias("申请时间")
    private Date applyTime;//申请时间
    @Alias("审核人工号")
    private Integer reviewId;//审核人id
    @Alias("审核备注")
    private String reviewRemark;//审核备注
    @Alias("审核时间")
    private Date reviewTime;//审核时间

}
