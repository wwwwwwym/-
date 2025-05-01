package com.example.wms_springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class logs {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String operation;
    private String type;
    private String user;
    private String time;
}
