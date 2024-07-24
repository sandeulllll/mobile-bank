package com.ccnu.mobilebank.config;


import com.ccnu.mobilebank.handler.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/users/user-tokens")
                .excludePathPatterns("/users/get-code")
                .excludePathPatterns("/users/verify-code")
                .excludePathPatterns("/users/register")
                .excludePathPatterns("/users/test")
                .excludePathPatterns("/users/password");
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("http://localhost:8080","http://localhost:3000")
                    .allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(true);
    }

    //映射/files/**到文件系统目录
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("files/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/files/");
    }
}

