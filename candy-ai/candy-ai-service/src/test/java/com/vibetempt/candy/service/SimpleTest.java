package com.vibetempt.candy.service;

import com.vibetempt.candy.domain.AiCharacter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleTest {

    @Test
    public void testBasicFunctionality() {
        // 测试基本的对象创建和getter/setter方法
        AiCharacter character = new AiCharacter();
        character.setName("Test Character");
        character.setDescription("A test character");
        character.setCharacterType("girls");
        character.setMembershipType("normal");
        
        assertEquals("Test Character", character.getName());
        assertEquals("A test character", character.getDescription());
        assertEquals("girls", character.getCharacterType());
        assertEquals("normal", character.getMembershipType());
        
        System.out.println("Basic test passed!");
    }
} 