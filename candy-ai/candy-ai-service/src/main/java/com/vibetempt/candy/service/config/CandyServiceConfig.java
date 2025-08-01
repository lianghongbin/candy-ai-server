package com.vibetempt.candy.service.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Candy AI Service 配置类
 * 确保 candy-ai-service 模块的 Mapper 被正确扫描
 * 
 * @author candy
 */
@Configuration
@MapperScan("com.vibetempt.candy.service.mapper")
public class CandyServiceConfig {
    
    // 这个配置类确保 candy-ai-service 模块的 Mapper 被正确扫描
} 