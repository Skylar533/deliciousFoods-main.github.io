package com.example.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyInterceptorConfig implements WebMvcConfigurer {
    /**
     * 实现 WebMvcConfigurer 接口的方法，用于配置拦截器。
     *
     * @param registry 拦截器注册器，用于添加拦截器和配置拦截规则。
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 创建一个自定义拦截器实例。
        registry.addInterceptor(new MyInterceptor())
                // 设置拦截的路径模式，这里表示拦截 /end/page/ 下的所有路径。
                .addPathPatterns("/end/page/**")
                // 设置排除的路径模式，即不拦截 /end/page/login.html 和 /end/page/register.html 这两个路径。
                .excludePathPatterns("/end/page/login.html", "/end/page/register.html");
    }
}