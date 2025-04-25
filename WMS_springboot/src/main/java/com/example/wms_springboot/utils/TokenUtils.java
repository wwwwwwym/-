package com.example.wms_springboot.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.wms_springboot.dao.UserDao;
import com.example.wms_springboot.entity.User;
import com.example.wms_springboot.service.impl.UserImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenUtils {

    private static UserImpl staticUserImpl;

    @Resource
    UserImpl userImpl;

    public void setUserService()
    {
        staticUserImpl =userImpl;
    }

//    生成token
    public static String createToken(String userId ,String sign)
    {
        return JWT.create().withAudience(userId)//将userid保存到token
                .withExpiresAt(DateUtil.offsetHour(new Date(),2))//2小时后token过期
                .sign(Algorithm.HMAC256(sign));//以password作为token密钥
    }

//    获取当前登录的用户信息
    public static User getCurrentUser()
    {
        try{
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if(StrUtil.isNotBlank(token))
            {
                String userId = JWT.decode(token).getAudience().get(0);
                return staticUserImpl.findById(Integer.valueOf(userId));
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }

}
