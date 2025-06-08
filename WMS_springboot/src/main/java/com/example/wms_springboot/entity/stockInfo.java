package com.example.wms_springboot.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 库存信息
 */
@Data
@TableName("stockinfo")
public class stockInfo {
    @TableId(type = IdType.AUTO)
    @Alias("记录序号")
    private Integer stockid;//存储记录id
    @Alias("所在仓库")
    private String deposity;//所在仓库
    @Alias("产品名称")
    private String pname;//产品名称
    @Alias("产品图片")
    private String pictures;//产品照片
    @Alias("产品数量")
    private Integer quantity;//产品数量
    @Alias("最低库存")
    private Integer threshold;//最低库存
    @Alias("产品价格")
    private BigDecimal price;//产品价格
    @Alias("入库时间")
    private String stocktime;//入库时间

}
