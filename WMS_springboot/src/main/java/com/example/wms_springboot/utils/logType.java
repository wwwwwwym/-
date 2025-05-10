package com.example.wms_springboot.utils;


public enum logType {

    ADD("新增"),
    UPDATE("修改"),
    DELETE("删除"),
    DELETE_BATCH("批量删除"),
    EXPORT("批量导出"),
    IMPORT("批量导入"),
    LOGIN("登录"),
    REGISTER("注册");

    private String value;

    public String getValue() {
        return value;
    }

    //构造函数
    logType(String value) {
        this.value = value;
    }
}
