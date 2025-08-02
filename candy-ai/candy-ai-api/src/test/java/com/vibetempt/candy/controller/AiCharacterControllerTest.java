package com.vibetempt.candy.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vibetempt.candy.domain.AiCharacter;
import com.vibetempt.candy.service.AiCharacterService;

/**
 * AI角色控制器单元测试
 * 专注于Controller业务逻辑，不涉及权限和安全问题
 *
 * @author vibetempt
 */
@WebMvcTest(AiCharacterController.class)
@ContextConfiguration(classes = {AiCharacterController.class})
class AiCharacterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AiCharacterService aiCharacterService;

    @Autowired
    private ObjectMapper objectMapper;

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
    void testGetCharacterList() throws Exception {
        // 准备
        when(aiCharacterService.getCharacterList(any(AiCharacter.class)))
                .thenReturn(testCharacterList);

        // 执行和验证
        mockMvc.perform(get("/candy/character/list")
                .param("name", "测试角色")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.rows").isArray())
                .andExpect(jsonPath("$.rows[0].name").value("测试角色"));

        verify(aiCharacterService).getCharacterList(any(AiCharacter.class));
    }

    @Test
    void testGetCharacterById() throws Exception {
        // 准备
        when(aiCharacterService.getCharacterById(1L)).thenReturn(testCharacter);

        // 执行和验证
        mockMvc.perform(get("/candy/character/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.name").value("测试角色"));

        verify(aiCharacterService).getCharacterById(1L);
    }

    @Test
    void testAddCharacter() throws Exception {
        // 准备
        when(aiCharacterService.createCharacter(any(AiCharacter.class)))
                .thenReturn(testCharacter);

        // 执行和验证
        mockMvc.perform(post("/candy/character")
                .content(objectMapper.writeValueAsString(testCharacter))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("新增成功"));

        verify(aiCharacterService).createCharacter(any(AiCharacter.class));
    }

    @Test
    void testUpdateCharacter() throws Exception {
        // 准备
        when(aiCharacterService.updateCharacter(any(AiCharacter.class)))
                .thenReturn(1);

        // 执行和验证
        mockMvc.perform(put("/candy/character")
                .content(objectMapper.writeValueAsString(testCharacter))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("修改成功"));

        verify(aiCharacterService).updateCharacter(any(AiCharacter.class));
    }

    @Test
    void testDeleteCharacter() throws Exception {
        // 准备
        when(aiCharacterService.deleteCharacter(1L)).thenReturn(1);

        // 执行和验证
        mockMvc.perform(delete("/candy/character/{ids}", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("删除成功"));

        verify(aiCharacterService).deleteCharacter(1L);
    }

    @Test
    void testGetUserCharacters() throws Exception {
        // 准备
        when(aiCharacterService.getUserCharacters(1L))
                .thenReturn(testCharacterList);

        // 执行和验证
        mockMvc.perform(get("/candy/character/user/{userId}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].name").value("测试角色"));

        verify(aiCharacterService).getUserCharacters(1L);
    }

    @Test
    void testGetSystemCharacters() throws Exception {
        // 准备
        when(aiCharacterService.getSystemCharacters())
                .thenReturn(testCharacterList);

        // 执行和验证
        mockMvc.perform(get("/candy/character/system")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].name").value("测试角色"));

        verify(aiCharacterService).getSystemCharacters();
    }

    @Test
    void testCopyCharacter() throws Exception {
        // 准备
        when(aiCharacterService.copyCharacter(1L, 2L))
                .thenReturn(testCharacter);

        // 执行和验证
        mockMvc.perform(post("/candy/character/copy")
                .param("sourceId", "1")
                .param("userId", "2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("复制成功"));

        verify(aiCharacterService).copyCharacter(1L, 2L);
    }

    @Test
    void testAddCharacter_ValidationError() throws Exception {
        // 准备：创建一个缺少必填字段的角色
        AiCharacter invalidCharacter = new AiCharacter();
        // 不设置name、characterType、membershipType等必填字段

        // 执行和验证
        mockMvc.perform(post("/candy/character")
                .content(objectMapper.writeValueAsString(invalidCharacter))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400));

        // 验证Service方法没有被调用
        verify(aiCharacterService, never()).createCharacter(any(AiCharacter.class));
    }

    @Test
    void testGetCharacterById_NotFound() throws Exception {
        // 准备
        when(aiCharacterService.getCharacterById(999L)).thenReturn(null);

        // 执行和验证
        mockMvc.perform(get("/candy/character/{id}", 999L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isEmpty());

        verify(aiCharacterService).getCharacterById(999L);
    }
} 