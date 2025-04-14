package com.example.wms_springboot.utils;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseResult { //封装统一的返回值类型

    private static final Integer SUCCESS=0;
    private static final Integer ERROR=1;
    private Integer code; //响应码
    private String msg; //提示信息
    private Object data;  // 存储的数据

//    public static ResponseResult<Object> returnMsg(Object[] params){
//        ResponseResult<Object> result = new ResponseResult<>();
//        result.setCode(Integer.parseInt(params[0].toString()));
//        result.setMsg(params[1].toString());
//        result.setData(params[2]);
//        return result;
//    }
    public static ResponseResult success(){
        ResponseResult result = new ResponseResult();
        result.setCode(SUCCESS);
        return result;
    }
    public static ResponseResult success(Object data){
        ResponseResult result = new ResponseResult();
        result.setCode(SUCCESS);
        result.setData(data);
        return result;
    }
    public static ResponseResult error(String msg){
        ResponseResult result = new ResponseResult();
        result.setCode(ERROR);
        result.setMsg(msg);
        return result;
    }
}
