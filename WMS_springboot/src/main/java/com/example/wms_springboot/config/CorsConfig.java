package com.example.wms_springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    private static final long MAX_AGE =24*60*60;

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source =new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration =new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setMaxAge(MAX_AGE);
        source.registerCorsConfiguration("/**" , configuration);//对接口配置跨域设置
        return new CorsFilter(source);


    }

}
