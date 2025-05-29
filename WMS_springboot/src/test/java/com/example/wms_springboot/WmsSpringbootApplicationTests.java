package com.example.wms_springboot;

import org.flowable.engine.ProcessEngine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WmsSpringbootApplicationTests {
    @Autowired

    private ProcessEngine processEngine;

    @Test
    void contextLoads() {
        System.out.println(processEngine);
    }

}
