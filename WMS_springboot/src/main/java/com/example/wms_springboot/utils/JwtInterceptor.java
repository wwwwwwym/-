package com.example.wms_springboot.utils;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.wms_springboot.entity.User;
import com.example.wms_springboot.exception.CustomException;
import com.example.wms_springboot.service.impl.UserImpl;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//JWT拦截器验证
public class JwtInterceptor implements HandlerInterceptor {

    @Resource
    private UserImpl userImpl;


    @Override
    public boolean preHandle(HttpServletRequest request , HttpServletResponse response , Object handler)
    {
        String token =request.getHeader("token");//前端请求数据的header里面传过来的token
        if(StrUtil.isBlank(token))
        {//url参数
            token=request.getParameter("token");
        }
        //执行认证
        if(StrUtil.isBlank(token))
        {
            throw new CustomException(ResultCode.TOKEN_INVALID_ERROR);
        }
        //获取token中的userid
        String userId;
        try{
            userId= JWT.decode(token).getAudience().get(0);//解码token，拿第一个数据，即userid
        }catch (JWTDecodeException j){
            throw new CustomException(ResultCode.TOKEN_INVALID_ERROR);
        }
        //根据userid到数据库查询
        Integer userid = Integer.valueOf(userId);
        User user = userImpl.selectById(userid);
        if(user==null)
        {
            throw new CustomException(ResultCode.USER_NOT_EXITS_ERROR);
        }

        //用户密码加密之后生成一个验证器（tokenUtils)

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try{
            jwtVerifier.verify(token);
        }catch(JWTVerificationException e){
            throw new CustomException(ResultCode.TOKEN_CHECK_ERROR);
        }
        return true;
    }
}
