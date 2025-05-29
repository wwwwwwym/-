package com.example.wms_springboot;

import org.flowable.engine.ProcessEngine;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLOutput;

@SpringBootApplication
@MapperScan("com.example.wms_springboot.dao")
public class WmsSpringbootApplication {


    public static void main(String[] args) {
        System.out.println("开始");
        SpringApplication.run(WmsSpringbootApplication.class, args);
        System.out.println("结束" );
    }

}
