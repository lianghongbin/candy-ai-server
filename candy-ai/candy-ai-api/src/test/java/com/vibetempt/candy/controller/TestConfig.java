package com.vibetempt.candy.controller;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Controller测试配置
 * 
 * @author vibetempt
 */
@TestConfiguration
@EnableWebMvc
@ComponentScan(basePackages = "com.vibetempt.candy.controller")
public class TestConfig {
    
    // 这里可以添加测试需要的Bean配置
    // 由于我们使用@WebMvcTest，Spring Boot会自动配置Web层相关的Bean
} 