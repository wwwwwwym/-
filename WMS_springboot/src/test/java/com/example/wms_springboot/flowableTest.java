package com.example.wms_springboot;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootTest
class DiagnosticTests {

    @Autowired
    private ApplicationContext context;

    @Test
    void printAllBeans() {
        System.out.println("======= Spring Beans List =======");
        String[] beanNames = context.getBeanDefinitionNames();
        Arrays.sort(beanNames);

        for (String beanName : beanNames) {
            Object bean = context.getBean(beanName);
            System.out.printf("%-50s : %s\n", beanName, bean.getClass().getSimpleName());
        }
        System.out.println("=================================");
    }

    @Test
    void checkFlowableBeans() {
        String[] flowableBeans = {
                "processEngine",
                "runtimeService",
                "repositoryService",
                "taskService",
                "historyService",
                "managementService"
        };

        for (String beanName : flowableBeans) {
            if (context.containsBean(beanName)) {
                System.out.printf("✅ Found Flowable bean: %s\n", beanName);
            } else {
                System.err.printf("❌ MISSING Flowable bean: %s\n", beanName);
            }
        }
    }
}
