package com.example.wms_springboot.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("deposity")
public class Deposity {
    @TableId(type = IdType.AUTO )
    @Alias("记录序号")
    private Integer deposityid;//记录id
    @Alias("仓库名称")
    private String dname;//仓库名称
    @Alias("仓库地址")
    private String address;//仓库地址
    @Alias("仓库简介")
    private String introduce;//仓库简介
}
