package com.vibetempt.candy.domain.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Candy AI Domain 配置类
 * 确保 candy-ai-domain 模块的 Mapper 被正确扫描
 * 
 * @author candy
 */
@Configuration
@MapperScan("com.vibetempt.candy.domain.mapper")
public class CandyDomainConfig {
    
    // 这个配置类确保 candy-ai-domain 模块的 Mapper 被正确扫描
} 