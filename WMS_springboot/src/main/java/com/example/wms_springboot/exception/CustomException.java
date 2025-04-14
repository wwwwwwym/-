package com.example.wms_springboot.exception;

import com.example.wms_springboot.utils.ResultCode;
import lombok.Data;

@Data
public class CustomException extends RuntimeException{
    private String code;
    private String msg;

    public  CustomException(ResultCode resultCode){
        this.code=resultCode.code;
        this.msg=resultCode.msg;
    }

    public  CustomException(String code,String msg){
        this.code=code;
        this.msg=msg;
    }


}
