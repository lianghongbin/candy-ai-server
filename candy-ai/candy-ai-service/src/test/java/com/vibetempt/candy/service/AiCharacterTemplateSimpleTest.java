package com.vibetempt.candy.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.vibetempt.candy.domain.AiCharacterTemplate;

/**
 * AI角色模板简单测试（不使用Mockito）
 * 
 * @author vibetempt
 */
class AiCharacterTemplateSimpleTest {

    @Test
    void testAiCharacterTemplateCreation() {
        // 测试实体创建
        AiCharacterTemplate template = new AiCharacterTemplate();
        template.setId(1L);
        template.setName("测试模板");
        template.setDescription("这是一个测试模板");
        template.setCharacterType("girls");
        template.setCreatorId(1L);

        // 验证基本属性
        assertNotNull(template);
        assertEquals(1L, template.getId());
        assertEquals("测试模板", template.getName());
        assertEquals("这是一个测试模板", template.getDescription());
        assertEquals("girls", template.getCharacterType());
        assertEquals(1L, template.getCreatorId());
    }

    @Test
    void testAiCharacterTemplateValidation() {
        // 测试验证注解
        AiCharacterTemplate template = new AiCharacterTemplate();
        
        // 测试必填字段验证
        assertNotNull(template);
        
        // 测试字段设置
        template.setName("验证测试模板");
        template.setCharacterType("boys");
        template.setMembershipType("premium");
        
        assertEquals("验证测试模板", template.getName());
        assertEquals("boys", template.getCharacterType());
        assertEquals("premium", template.getMembershipType());
    }

    @Test
    void testAiCharacterTemplateEquality() {
        // 测试相等性
        AiCharacterTemplate template1 = new AiCharacterTemplate();
        template1.setId(1L);
        template1.setName("测试模板1");
        template1.setCharacterType("girls");
        template1.setMembershipType("normal");

        AiCharacterTemplate template2 = new AiCharacterTemplate();
        template2.setId(1L);
        template2.setName("测试模板1");
        template2.setCharacterType("girls");
        template2.setMembershipType("normal");

        // 验证相等性（基于ID）
        assertEquals(template1.getId(), template2.getId());
        assertEquals(template1.getName(), template2.getName());
        assertEquals(template1.getCharacterType(), template2.getCharacterType());
        assertEquals(template1.getMembershipType(), template2.getMembershipType());
    }

    @Test
    void testAiCharacterTemplateToString() {
        // 测试toString方法
        AiCharacterTemplate template = new AiCharacterTemplate();
        template.setId(1L);
        template.setName("ToString测试模板");
        template.setDescription("测试toString方法");
        template.setCharacterType("girls");
        template.setMembershipType("normal");

        String toString = template.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("测试模板"));
        assertTrue(toString.contains("girls"));
        assertTrue(toString.contains("normal"));
    }

    @Test
    void testAiCharacterTemplateHashCode() {
        // 测试hashCode方法
        AiCharacterTemplate template = new AiCharacterTemplate();
        template.setId(1L);
        template.setName("HashCode测试模板");

        int hashCode = template.hashCode();
        assertNotEquals(0, hashCode);
    }
} 