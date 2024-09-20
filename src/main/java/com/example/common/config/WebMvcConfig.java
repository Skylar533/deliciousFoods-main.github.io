package com.example.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置类，用于设置跨域访问。
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 配置跨域访问规则。
     * 允许所有路径（"/**"）的跨域请求，设置允许的请求头、方法、源和是否允许携带凭证。
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("Content-Type","X-Requested-With","accept,Origin","Access-Control-Request-Method","Access-Control-Request-Headers","token")
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowCredentials(true);
    }
}