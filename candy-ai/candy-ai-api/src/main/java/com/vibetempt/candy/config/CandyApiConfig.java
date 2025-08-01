package com.vibetempt.candy.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Candy AI API 配置类
 * 确保 candy-ai 模块的组件被正确扫描
 * 
 * @author candy
 */
@Configuration
@ComponentScan(basePackages = {
    "com.vibetempt.candy.service",
    "com.vibetempt.candy.service.impl",
    "com.vibetempt.candy.service.mapper"
})
public class CandyApiConfig {
    
    // 这个配置类确保 candy-ai 模块的 Service 和 Mapper 被正确扫描
} 