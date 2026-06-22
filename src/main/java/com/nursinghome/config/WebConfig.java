package com.nursinghome.config;

import com.nursinghome.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")                    // 拦截所有请求
                .excludePathPatterns(                       // 放行以下路径
                        "/login",                           // 登录页面和接口
                        "/logout",                          // 退出登录
                        "/css/**",                          // 静态资源
                        "/js/**",
                        "/images/**",
                        "/fonts/**",
                        "/error"                            // 错误页面
                );
    }
}
