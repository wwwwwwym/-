package com.example.wms_springboot.config;


import com.example.wms_springboot.utils.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
//拦截器规则配置
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(jwtInterceptor())//配置JWT拦截器规则
                .addPathPatterns("/**")//拦截所有请求路径
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/register")
                .excludePathPatterns("/**")
//                .excludePathPatterns("/file/**")
                .excludePathPatterns("/user/forgetPass");


//        super.addInterceptors(registry);
    }

    @Bean
    public JwtInterceptor jwtInterceptor()
    {
        return new JwtInterceptor();
    }
}
