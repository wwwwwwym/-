package com.example.wms_springboot.entity;

import lombok.Data;

import javax.persistence.Entity;
import java.util.Date;

@Data
@Entity
public class deposityRecord {

    private Integer record_id;//记录id
    private String pname;//产品名称
    private Integer deposity_id;//仓库id
    private Integer type;//出入库类型
    private Integer quantity;//产品数量
    private Double price;//产品价格
    private Integer state;//当前状态
    private Integer apply_id;//申请人id
    private String apply_remark;//申请备注
    private Date apply_time;//申请时间
    private Integer review_id;//审核人id
    private String review_remark;//审核备注
    private Date review_time;//审核时间

}
