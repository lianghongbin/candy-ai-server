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
import com.vibetempt.candy.domain.AiCharacterTemplate;
import com.vibetempt.candy.domain.mapper.AiCharacterTemplateMapper;
import com.vibetempt.candy.service.impl.AiCharacterTemplateServiceImpl;

/**
 * AI角色模板服务单元测试
 *
 * @author vibetempt
 */
@ExtendWith(MockitoExtension.class)
class AiCharacterTemplateServiceTest {

    @Mock
    private AiCharacterTemplateMapper aiCharacterTemplateMapper;

    @InjectMocks
    private AiCharacterTemplateServiceImpl aiCharacterTemplateService;

    private AiCharacterTemplate testTemplate;
    private List<AiCharacterTemplate> testTemplateList;

    @BeforeEach
    void setUp() {
        // 准备测试数据
        testTemplate = new AiCharacterTemplate();
        testTemplate.setId(1L);
        testTemplate.setName("测试模板");
        testTemplate.setDescription("这是一个测试模板");
        testTemplate.setCharacterType("girls");
        testTemplate.setCreatorId(1L);

        testTemplateList = Arrays.asList(testTemplate);
    }

    @Test
    void testSelectAiCharacterTemplateList_Success() {
        // 准备
        when(aiCharacterTemplateMapper.selectAiCharacterTemplateList(any(AiCharacterTemplate.class)))
                .thenReturn(testTemplateList);

        // 执行
        List<AiCharacterTemplate> result = aiCharacterTemplateService.selectAiCharacterTemplateList(testTemplate);

        // 验证
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("测试模板", result.get(0).getName());
        verify(aiCharacterTemplateMapper).selectAiCharacterTemplateList(testTemplate);
    }

    @Test
    void testSelectAiCharacterTemplateById_Success() {
        // 准备
        when(aiCharacterTemplateMapper.selectAiCharacterTemplateById(1L)).thenReturn(testTemplate);

        // 执行
        AiCharacterTemplate result = aiCharacterTemplateService.selectAiCharacterTemplateById(1L);

        // 验证
        assertNotNull(result);
        assertEquals("测试模板", result.getName());
        verify(aiCharacterTemplateMapper).selectAiCharacterTemplateById(1L);
    }

    @Test
    void testInsertAiCharacterTemplate_Success() {
        // 准备
        when(aiCharacterTemplateMapper.insertAiCharacterTemplate(any(AiCharacterTemplate.class))).thenReturn(1);

        // 使用MockedStatic模拟SecurityUtils
        try (MockedStatic<SecurityUtils> securityUtilsMock = mockStatic(SecurityUtils.class)) {
            securityUtilsMock.when(SecurityUtils::getUsername).thenReturn("admin");

            // 执行
            int result = aiCharacterTemplateService.insertAiCharacterTemplate(testTemplate);

            // 验证
            assertEquals(1, result);
            verify(aiCharacterTemplateMapper).insertAiCharacterTemplate(testTemplate);
        }
    }

    @Test
    void testUpdateAiCharacterTemplate_Success() {
        // 准备
        when(aiCharacterTemplateMapper.updateAiCharacterTemplate(any(AiCharacterTemplate.class))).thenReturn(1);

        // 使用MockedStatic模拟SecurityUtils
        try (MockedStatic<SecurityUtils> securityUtilsMock = mockStatic(SecurityUtils.class)) {
            securityUtilsMock.when(SecurityUtils::getUsername).thenReturn("admin");

            // 执行
            int result = aiCharacterTemplateService.updateAiCharacterTemplate(testTemplate);

            // 验证
            assertEquals(1, result);
            verify(aiCharacterTemplateMapper).updateAiCharacterTemplate(testTemplate);
        }
    }

    @Test
    void testDeleteAiCharacterTemplateById_Success() {
        // 准备
        when(aiCharacterTemplateMapper.deleteAiCharacterTemplateById(1L)).thenReturn(1);

        // 执行
        int result = aiCharacterTemplateService.deleteAiCharacterTemplateById(1L);

        // 验证
        assertEquals(1, result);
        verify(aiCharacterTemplateMapper).deleteAiCharacterTemplateById(1L);
    }

    @Test
    void testDeleteAiCharacterTemplateByIds_Success() {
        // 准备
        Long[] ids = {1L, 2L};
        when(aiCharacterTemplateMapper.deleteAiCharacterTemplateByIds(ids)).thenReturn(2);

        // 执行
        int result = aiCharacterTemplateService.deleteAiCharacterTemplateByIds(ids);

        // 验证
        assertEquals(2, result);
        verify(aiCharacterTemplateMapper).deleteAiCharacterTemplateByIds(ids);
    }
} 