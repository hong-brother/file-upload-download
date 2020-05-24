package com.hsnam.spring.file.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

public class WebConfig implements WebMvcConfigurer {
    @Value("${file.upload}")
    private String staticResourceLocation;

    /**
     * 외부 static Resource 지정
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**")
                .addResourceLocations(Paths.get(staticResourceLocation).toUri().toString());
    }

}
