package com.example.wms_springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLOutput;

@SpringBootApplication

public class WmsSpringbootApplication {

    public static void main(String[] args) {
        System.out.println("开始");
        SpringApplication.run(WmsSpringbootApplication.class, args);
        System.out.println("结束");
    }

}
