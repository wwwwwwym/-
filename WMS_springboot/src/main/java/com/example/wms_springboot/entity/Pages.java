package com.example.wms_springboot.entity;

import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;

@Data
@Entity
public class Pages implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer pageSize;  //每页行数
	private Integer pageNumber; //当前页
}
