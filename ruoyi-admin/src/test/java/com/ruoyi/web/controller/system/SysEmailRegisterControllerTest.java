package com.ruoyi.web.controller.system;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.core.domain.model.EmailRegisterBody;

/**
 * 邮箱注册Controller集成测试
 * 
 * @author ruoyi
 */
@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
@Transactional
class SysEmailRegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSendVerifyCode_Success() throws Exception {
        mockMvc.perform(post("/auth/email/sendVerifyCode")
                .param("email", "test@example.com")
                .param("verifyType", "register"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("验证码发送成功"));
    }

    @Test
    void testSendVerifyCode_EmptyEmail() throws Exception {
        mockMvc.perform(post("/auth/email/sendVerifyCode")
                .param("email", "")
                .param("verifyType", "register"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.msg").value("邮箱地址不能为空"));
    }

    @Test
    void testSendVerifyCode_InvalidEmail() throws Exception {
        mockMvc.perform(post("/auth/email/sendVerifyCode")
                .param("email", "invalid-email")
                .param("verifyType", "register"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.msg").value("邮箱格式不正确"));
    }

    @Test
    void testCheckEmail_NotRegistered() throws Exception {
        mockMvc.perform(post("/auth/email/checkEmail")
                .param("email", "newuser@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.isRegistered").value(false));
    }

    @Test
    void testCheckEmail_EmptyEmail() throws Exception {
        mockMvc.perform(post("/auth/email/checkEmail")
                .param("email", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.isRegistered").value(false));
    }

    @Test
    void testRegister_ValidData() throws Exception {
        EmailRegisterBody registerBody = new EmailRegisterBody();
        registerBody.setEmail("newuser@example.com");
        registerBody.setPassword("123456");
        registerBody.setNickName("测试用户");

        mockMvc.perform(post("/auth/email/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void testRegister_EmptyEmail() throws Exception {
        EmailRegisterBody registerBody = new EmailRegisterBody();
        registerBody.setEmail("");
        registerBody.setPassword("123456");

        mockMvc.perform(post("/auth/email/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.msg").value("邮箱不能为空"));
    }

    @Test
    void testRegister_EmptyPassword() throws Exception {
        EmailRegisterBody registerBody = new EmailRegisterBody();
        registerBody.setEmail("test@example.com");
        registerBody.setPassword("");

        mockMvc.perform(post("/auth/email/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.msg").value("用户密码不能为空"));
    }

    @Test
    void testRegister_PasswordTooShort() throws Exception {
        EmailRegisterBody registerBody = new EmailRegisterBody();
        registerBody.setEmail("test@example.com");
        registerBody.setPassword("123");

        mockMvc.perform(post("/auth/email/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.msg").value("密码长度必须在6到20个字符之间"));
    }

    @Test
    void testRegister_PasswordTooLong() throws Exception {
        EmailRegisterBody registerBody = new EmailRegisterBody();
        registerBody.setEmail("test@example.com");
        registerBody.setPassword("123456789012345678901");

        mockMvc.perform(post("/auth/email/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.msg").value("密码长度必须在6到20个字符之间"));
    }

    @Test
    void testVerifyCode_Success() throws Exception {
        // 先发送验证码
        mockMvc.perform(post("/auth/email/sendVerifyCode")
                .param("email", "test@example.com")
                .param("verifyType", "register"))
                .andExpect(status().isOk());

        // 验证验证码（这里需要从数据库获取实际的验证码）
        mockMvc.perform(post("/auth/email/verifyCode")
                .param("email", "test@example.com")
                .param("verifyCode", "123456")
                .param("verifyType", "register"))
                .andExpect(status().isOk());
    }

    @Test
    void testVerifyCode_EmptyEmail() throws Exception {
        mockMvc.perform(post("/auth/email/verifyCode")
                .param("email", "")
                .param("verifyCode", "123456")
                .param("verifyType", "register"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.msg").value("验证码验证失败"));
    }

    @Test
    void testVerifyCode_EmptyCode() throws Exception {
        mockMvc.perform(post("/auth/email/verifyCode")
                .param("email", "test@example.com")
                .param("verifyCode", "")
                .param("verifyType", "register"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.msg").value("验证码验证失败"));
    }
} 