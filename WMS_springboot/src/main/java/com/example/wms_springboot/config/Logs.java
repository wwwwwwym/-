package com.example.wms_springboot.config;

import com.example.wms_springboot.utils.logType;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Logs {
    //操作模块
    String operation();
//    操作类型
    logType type();
}
