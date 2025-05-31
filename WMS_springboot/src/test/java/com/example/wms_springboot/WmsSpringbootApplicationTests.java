package com.example.wms_springboot;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RuntimeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = WmsSpringbootApplication.class)
class WmsSpringbootApplicationTests {
    @Autowired

    private ProcessEngine processEngine;

    @Autowired
    private RuntimeService runtimeService;
    @Test
    void contextLoads() {
        assertNotNull(processEngine, "ProcessEngine 注入失败");
        System.out.println("Flowable 引擎初始化成功");
        System.out.println("数据库类型: " +
                processEngine.getProcessEngineConfiguration().getDatabaseType());
    }

}
