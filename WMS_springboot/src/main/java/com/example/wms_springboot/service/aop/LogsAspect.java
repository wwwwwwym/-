package com.example.wms_springboot.service.aop;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.ArrayUtil;
import com.example.wms_springboot.config.Logs;
import com.example.wms_springboot.entity.User;

import com.example.wms_springboot.entity.logs;
import com.example.wms_springboot.service.ILogsService;
import com.example.wms_springboot.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Aspect
@Slf4j
public class LogsAspect {
    @Resource
    ILogsService logsService;

    @AfterReturning(pointcut = "@annotation(Logs)",returning = "jsonResult")
    public void recordLog(JoinPoint joinPoint, Logs Logs, Object jsonResult){
        User loginUser = TokenUtils.getCurrentUser();
        if(loginUser==null){
            Object[] args = joinPoint.getArgs();
            if(ArrayUtil.isNotEmpty(args)){
                if(args[0] instanceof  User){
                    loginUser = (User) args[0];
                }
            }
        }
        if(loginUser ==null){
            log.error("未获取当前操作用户信息");
            return;
        }

        logs log_build = logs.builder()
                .user(loginUser.getUsername())
                .operation(Logs.operation())
                .type(Logs.type().getValue())
                .time(DateUtil.now())
                .build();
        //插入数据
//        logsService.save(log_build);
        ThreadUtil.execAsync(() ->{
            logsService.save(log_build);
        });//异步插入，防止插入出错，影响业务操作
    }
}
