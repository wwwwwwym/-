package com.example.wms_springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Entity;
import java.util.Date;

/**
 * 库存转移信息
 */
@Data
@TableName("transferrecord")
public class transferRecord {
    @TableId(type = IdType.AUTO)
    private Integer id;//记录id
    private String pname;//产品名称
    private String picture;//产品照片
    private String from;//来源仓库
    private String to;//目的仓库
    private Integer quantity;//产品数量
    private Double price;//产品价格
    private Integer state;//当前状态
    private Integer applyId;//申请人id
    private String applyRemark;//申请备注
    private String applyTime;//申请时间
    private Integer reviewId;//审核人id
    private String reviewRemark;//审核备注
    private String reviewTime;//审核时间


}
