package com.example.wms_springboot;

import org.flowable.engine.ProcessEngine;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.flowable.engine.ProcessEngine;

import javax.annotation.PostConstruct;
import java.sql.SQLOutput;

@SpringBootApplication
@MapperScan("com.example.wms_springboot.dao")
public class WmsSpringbootApplication {
    @Autowired // Spring 注入
    private ProcessEngine processEngine;
    @PostConstruct // Spring 初始化后执行
    public void init() {
        if (processEngine == null) {
            throw new IllegalStateException("ProcessEngine 注入失败！");
        }
        System.out.println("ProcessEngine 注入成功，引擎名称: " + processEngine.getName());
    }

    public static void main(String[] args) {
        System.out.println("开始");
        SpringApplication.run(WmsSpringbootApplication.class, args);
        System.out.println("结束" );
    }

}
