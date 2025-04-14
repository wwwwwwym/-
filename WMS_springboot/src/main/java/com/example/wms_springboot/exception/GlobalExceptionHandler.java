package com.example.wms_springboot.exception;


//统一异常处理

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.example.wms_springboot.utils.ResponseResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(basePackages = "com.example.wms_springboot.controller")
public class GlobalExceptionHandler {

    private static final Log log= LogFactory.get();

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult error(HttpServletRequest request, Exception e)
    {
        log.error("异常信息",e);
        return ResponseResult.error(e.getMessage());
    }

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult customError(HttpServletRequest request, CustomException e)
    {
        return ResponseResult.error(e.getMsg());
    }

}
