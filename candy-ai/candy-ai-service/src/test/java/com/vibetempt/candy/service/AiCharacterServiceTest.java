package com.vibetempt.candy.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ruoyi.common.utils.SecurityUtils;
import com.vibetempt.candy.domain.AiCharacter;
import com.vibetempt.candy.domain.mapper.AiCharacterMapper;
import com.vibetempt.candy.service.impl.AiCharacterServiceImpl;

/**
 * AI角色服务单元测试
 *
 * @author vibetempt
 */
@ExtendWith(MockitoExtension.class)
class AiCharacterServiceTest {

    @Mock
    private AiCharacterMapper aiCharacterMapper;

    @InjectMocks
    private AiCharacterServiceImpl aiCharacterService;

    private AiCharacter testCharacter;
    private List<AiCharacter> testCharacterList;

    @BeforeEach
    void setUp() {
        // 准备测试数据
        testCharacter = new AiCharacter();
        testCharacter.setId(1L);
        testCharacter.setName("测试角色");
        testCharacter.setDescription("这是一个测试角色");
        testCharacter.setCharacterType("girls");
        testCharacter.setMembershipType("normal");
        testCharacter.setCreatorId(1L);

        testCharacterList = Arrays.asList(testCharacter);
    }

    @Test
    void testCreateCharacter_Success() {
        // 准备
        when(aiCharacterMapper.insertAiCharacter(any(AiCharacter.class))).thenReturn(1);

        // 执行
        AiCharacter result = aiCharacterService.createCharacter(testCharacter);

        // 验证
        assertNotNull(result);
        assertEquals("测试角色", result.getName());
        verify(aiCharacterMapper).insertAiCharacter(testCharacter);
    }

    @Test
    void testUpdateCharacter_Success() {
        // 准备
        when(aiCharacterMapper.updateAiCharacter(any(AiCharacter.class))).thenReturn(1);

        // 使用MockedStatic模拟SecurityUtils
        try (MockedStatic<SecurityUtils> securityUtilsMock = mockStatic(SecurityUtils.class)) {
            securityUtilsMock.when(SecurityUtils::getUsername).thenReturn("admin");

            // 执行
            int result = aiCharacterService.updateCharacter(testCharacter);

            // 验证
            assertEquals(1, result);
            verify(aiCharacterMapper).updateAiCharacter(testCharacter);
        }
    }

    @Test
    void testDeleteCharacter_Success() {
        // 准备
        when(aiCharacterMapper.deleteAiCharacterById(1L)).thenReturn(1);

        // 执行
        int result = aiCharacterService.deleteCharacter(1L);

        // 验证
        assertEquals(1, result);
        verify(aiCharacterMapper).deleteAiCharacterById(1L);
    }

    @Test
    void testGetCharacterList_Success() {
        // 准备
        when(aiCharacterMapper.selectAiCharacterList(any(AiCharacter.class)))
                .thenReturn(testCharacterList);

        // 执行
        List<AiCharacter> result = aiCharacterService.getCharacterList(testCharacter);

        // 验证
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("测试角色", result.get(0).getName());
        verify(aiCharacterMapper).selectAiCharacterList(testCharacter);
    }

    @Test
    void testGetCharacterById_Success() {
        // 准备
        when(aiCharacterMapper.selectAiCharacterById(1L)).thenReturn(testCharacter);

        // 执行
        AiCharacter result = aiCharacterService.getCharacterById(1L);

        // 验证
        assertNotNull(result);
        assertEquals("测试角色", result.getName());
        verify(aiCharacterMapper).selectAiCharacterById(1L);
    }

    @Test
    void testGetUserCharacters_Success() {
        // 准备
        when(aiCharacterMapper.selectAiCharacterListByCreator(1L)).thenReturn(testCharacterList);

        // 执行
        List<AiCharacter> result = aiCharacterService.getUserCharacters(1L);

        // 验证
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("测试角色", result.get(0).getName());
        verify(aiCharacterMapper).selectAiCharacterListByCreator(1L);
    }

    @Test
    void testGetSystemCharacters_Success() {
        // 准备
        when(aiCharacterMapper.selectSystemCharacters()).thenReturn(testCharacterList);

        // 执行
        List<AiCharacter> result = aiCharacterService.getSystemCharacters();

        // 验证
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("测试角色", result.get(0).getName());
        verify(aiCharacterMapper).selectSystemCharacters();
    }

    @Test
    void testCopyCharacter_Success() {
        // 准备
        AiCharacter sourceCharacter = new AiCharacter();
        sourceCharacter.setId(1L);
        sourceCharacter.setName("源角色");
        sourceCharacter.setDescription("源角色描述");
        sourceCharacter.setCreatorId(1L);

        when(aiCharacterMapper.selectAiCharacterById(1L)).thenReturn(sourceCharacter);
        when(aiCharacterMapper.insertAiCharacter(any(AiCharacter.class))).thenReturn(1);

        // 使用MockedStatic模拟SecurityUtils
        try (MockedStatic<SecurityUtils> securityUtilsMock = mockStatic(SecurityUtils.class)) {
            securityUtilsMock.when(SecurityUtils::getUsername).thenReturn("admin");

            // 执行
            AiCharacter result = aiCharacterService.copyCharacter(1L, 2L);

            // 验证
            assertNotNull(result);
            assertEquals("源角色_副本", result.getName());
            assertEquals(2L, result.getCreatorId());
            verify(aiCharacterMapper).selectAiCharacterById(1L);
            verify(aiCharacterMapper).insertAiCharacter(any(AiCharacter.class));
        }
    }

    // 测试旧的Mapper方法
    @Test
    void testSelectAiCharacterList_Success() {
        // 准备
        when(aiCharacterMapper.selectAiCharacterList(any(AiCharacter.class)))
                .thenReturn(testCharacterList);

        // 执行
        List<AiCharacter> result = aiCharacterService.selectAiCharacterList(testCharacter);

        // 验证
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("测试角色", result.get(0).getName());
        verify(aiCharacterMapper).selectAiCharacterList(testCharacter);
    }

    @Test
    void testSelectAiCharacterById_Success() {
        // 准备
        when(aiCharacterMapper.selectAiCharacterById(1L)).thenReturn(testCharacter);

        // 执行
        AiCharacter result = aiCharacterService.selectAiCharacterById(1L);

        // 验证
        assertNotNull(result);
        assertEquals("测试角色", result.getName());
        verify(aiCharacterMapper).selectAiCharacterById(1L);
    }

    @Test
    void testInsertAiCharacter_Success() {
        // 准备
        when(aiCharacterMapper.insertAiCharacter(any(AiCharacter.class))).thenReturn(1);

        // 执行
        int result = aiCharacterService.insertAiCharacter(testCharacter);

        // 验证
        assertEquals(1, result);
        verify(aiCharacterMapper).insertAiCharacter(testCharacter);
    }

    @Test
    void testUpdateAiCharacter_Success() {
        // 准备
        when(aiCharacterMapper.updateAiCharacter(any(AiCharacter.class))).thenReturn(1);

        // 执行
        int result = aiCharacterService.updateAiCharacter(testCharacter);

        // 验证
        assertEquals(1, result);
        verify(aiCharacterMapper).updateAiCharacter(testCharacter);
    }

    @Test
    void testDeleteAiCharacterById_Success() {
        // 准备
        when(aiCharacterMapper.deleteAiCharacterById(1L)).thenReturn(1);

        // 执行
        int result = aiCharacterService.deleteAiCharacterById(1L);

        // 验证
        assertEquals(1, result);
        verify(aiCharacterMapper).deleteAiCharacterById(1L);
    }

    @Test
    void testDeleteAiCharacterByIds_Success() {
        // 准备
        Long[] ids = {1L, 2L};
        when(aiCharacterMapper.deleteAiCharacterByIds(ids)).thenReturn(2);

        // 执行
        int result = aiCharacterService.deleteAiCharacterByIds(ids);

        // 验证
        assertEquals(2, result);
        verify(aiCharacterMapper).deleteAiCharacterByIds(ids);
    }
} 