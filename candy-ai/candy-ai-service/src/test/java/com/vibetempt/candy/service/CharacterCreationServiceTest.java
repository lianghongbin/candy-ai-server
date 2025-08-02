package com.vibetempt.candy.service;

import com.vibetempt.candy.domain.AiCharacter;
import com.vibetempt.candy.domain.AiCharacterCreationSession;
import com.vibetempt.candy.domain.dto.CharacterCreationConfig;
import com.vibetempt.candy.domain.dto.StepOptions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CharacterCreationServiceTest {

    @Test
    public void testBasicEntityCreation() {
        // 测试基本的实体创建和getter/setter方法
        AiCharacter character = new AiCharacter();
        character.setName("Test Character");
        character.setDescription("A test character for testing");
        character.setStyle("realistic");
        character.setEthnicity("asian");
        character.setAge("25");
        character.setEyeColor("brown");
        character.setHairStyle("long");
        character.setHairColor("black");
        character.setBodyType("slim");
        character.setBreastSize("medium");
        character.setButtSize("medium");
        character.setPersonality("friendly");
        character.setOccupation("student");
        character.setHobbies("reading,music");
        character.setRelationship("friend");
        character.setCharacterType("girls");
        character.setMembershipType("normal");
        character.setIsSystem("1");
        character.setStatus("active");
        character.setCreatorId(1L);

        // 验证创建的角色
        assertEquals("Test Character", character.getName());
        assertEquals("A test character for testing", character.getDescription());
        assertEquals("realistic", character.getStyle());
        assertEquals("asian", character.getEthnicity());
        assertEquals("25", character.getAge());
        assertEquals("brown", character.getEyeColor());
        assertEquals("long", character.getHairStyle());
        assertEquals("black", character.getHairColor());
        assertEquals("slim", character.getBodyType());
        assertEquals("medium", character.getBreastSize());
        assertEquals("medium", character.getButtSize());
        assertEquals("friendly", character.getPersonality());
        assertEquals("student", character.getOccupation());
        assertEquals("reading,music", character.getHobbies());
        assertEquals("friend", character.getRelationship());
        assertEquals("girls", character.getCharacterType());
        assertEquals("normal", character.getMembershipType());
        assertEquals("1", character.getIsSystem());
        assertEquals("active", character.getStatus());
        assertEquals(1L, character.getCreatorId());
        
        System.out.println("Character entity test passed!");
    }

    @Test
    public void testSessionEntityCreation() {
        // 测试会话实体的创建和getter/setter方法
        AiCharacterCreationSession session = new AiCharacterCreationSession();
        session.setUserId(1L);
        session.setSessionId("test-session-123");
        session.setCurrentStep("style");
        session.setStatus("active");
        
        assertEquals(1L, session.getUserId());
        assertEquals("test-session-123", session.getSessionId());
        assertEquals("style", session.getCurrentStep());
        assertEquals("active", session.getStatus());
        
        System.out.println("Session entity test passed!");
    }

    @Test
    public void testConfigEntityCreation() {
        // 测试配置实体的创建和getter/setter方法
        CharacterCreationConfig config = new CharacterCreationConfig();
        config.setSteps(null); // 这里设置为null，实际使用时会有具体的步骤配置
        
        assertNull(config.getSteps());
        
        System.out.println("Config entity test passed!");
    }

    @Test
    public void testStepOptionsEntityCreation() {
        // 测试步骤选项实体的创建和getter/setter方法
        StepOptions stepOptions = new StepOptions();
        stepOptions.setStepCode("style");
        stepOptions.setTitle("Style");
        stepOptions.setDescription("Choose your character style");
        
        assertEquals("style", stepOptions.getStepCode());
        assertEquals("Style", stepOptions.getTitle());
        assertEquals("Choose your character style", stepOptions.getDescription());
        
        System.out.println("StepOptions entity test passed!");
    }
} 