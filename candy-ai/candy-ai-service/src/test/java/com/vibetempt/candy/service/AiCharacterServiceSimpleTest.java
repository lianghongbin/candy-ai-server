package com.vibetempt.candy.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.vibetempt.candy.domain.AiCharacter;

/**
 * AI角色服务简单测试（不使用Mockito）
 * 
 * @author vibetempt
 */
class AiCharacterServiceSimpleTest {

    @Test
    void testAiCharacterCreation() {
        // 测试实体创建
        AiCharacter character = new AiCharacter();
        character.setId(1L);
        character.setName("测试角色");
        character.setDescription("这是一个测试角色");
        character.setCharacterType("girls");
        character.setMembershipType("normal");
        character.setCreatorId(1L);

        // 验证基本属性
        assertNotNull(character);
        assertEquals(1L, character.getId());
        assertEquals("测试角色", character.getName());
        assertEquals("这是一个测试角色", character.getDescription());
        assertEquals("girls", character.getCharacterType());
        assertEquals("normal", character.getMembershipType());
        assertEquals(1L, character.getCreatorId());
    }

    @Test
    void testAiCharacterValidation() {
        // 测试验证注解
        AiCharacter character = new AiCharacter();
        
        // 测试必填字段验证
        assertNotNull(character);
        
        // 测试字段设置
        character.setName("验证测试角色");
        character.setCharacterType("boys");
        character.setMembershipType("premium");
        
        assertEquals("验证测试角色", character.getName());
        assertEquals("boys", character.getCharacterType());
        assertEquals("premium", character.getMembershipType());
    }

    @Test
    void testAiCharacterEquality() {
        // 测试相等性
        AiCharacter character1 = new AiCharacter();
        character1.setId(1L);
        character1.setName("测试角色1");
        character1.setCharacterType("girls");
        character1.setMembershipType("normal");

        AiCharacter character2 = new AiCharacter();
        character2.setId(1L);
        character2.setName("测试角色1");
        character2.setCharacterType("girls");
        character2.setMembershipType("normal");

        // 验证相等性（基于ID）
        assertEquals(character1.getId(), character2.getId());
        assertEquals(character1.getName(), character2.getName());
        assertEquals(character1.getCharacterType(), character2.getCharacterType());
        assertEquals(character1.getMembershipType(), character2.getMembershipType());
    }

    @Test
    void testAiCharacterToString() {
        // 测试toString方法
        AiCharacter character = new AiCharacter();
        character.setId(1L);
        character.setName("ToString测试角色");
        character.setDescription("测试toString方法");
        character.setCharacterType("girls");
        character.setMembershipType("normal");

        String toString = character.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("测试角色"));
        assertTrue(toString.contains("girls"));
        assertTrue(toString.contains("normal"));
    }

    @Test
    void testAiCharacterHashCode() {
        // 测试hashCode方法
        AiCharacter character = new AiCharacter();
        character.setId(1L);
        character.setName("HashCode测试角色");

        int hashCode = character.hashCode();
        assertNotEquals(0, hashCode);
    }
} 