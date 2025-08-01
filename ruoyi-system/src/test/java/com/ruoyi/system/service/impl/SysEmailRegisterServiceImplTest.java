package com.ruoyi.system.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ruoyi.common.core.domain.entity.SysEmailVerify;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.EmailRegisterBody;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.system.mapper.SysEmailVerifyMapper;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysUserService;

/**
 * 邮箱注册服务单元测试
 * 
 * @author ruoyi
 */
@ExtendWith(MockitoExtension.class)
class SysEmailRegisterServiceImplTest {

    @Mock
    private ISysUserService userService;

    @Mock
    private ISysConfigService configService;

    @Mock
    private SysEmailVerifyMapper emailVerifyMapper;

    // @Mock
    // private RedisCache redisCache;

    @InjectMocks
    private SysEmailRegisterServiceImpl emailRegisterService;

    private EmailRegisterBody validRegisterBody;
    private SysUser mockUser;

    @BeforeEach
    void setUp() {
        // 准备测试数据
        validRegisterBody = new EmailRegisterBody();
        validRegisterBody.setEmail("test@example.com");
        validRegisterBody.setPassword("123456");
        validRegisterBody.setCode("1234");
        validRegisterBody.setUuid("test-uuid");
        validRegisterBody.setNickName("测试用户");

        mockUser = new SysUser();
        mockUser.setUserId(1L);
        mockUser.setUserName("test");
        mockUser.setEmail("test@example.com");
        mockUser.setStatus("0");
    }

    @Test
    void testRegister_Success() {
        // 准备
        when(configService.selectCaptchaEnabled()).thenReturn(false);
        when(userService.checkEmailUnique(any(SysUser.class))).thenReturn(true);
        when(userService.checkUserNameUnique(any(SysUser.class))).thenReturn(true);
        when(userService.registerUser(any(SysUser.class))).thenReturn(true);

        // 执行
        String result = emailRegisterService.register(validRegisterBody);

        // 验证
        assertEquals("", result);
        verify(userService).registerUser(any(SysUser.class));
    }

    @Test
    void testRegister_EmailEmpty() {
        // 准备
        validRegisterBody.setEmail("");

        // 执行
        String result = emailRegisterService.register(validRegisterBody);

        // 验证
        assertEquals("邮箱不能为空", result);
        verify(userService, never()).registerUser(any(SysUser.class));
    }

    @Test
    void testRegister_PasswordEmpty() {
        // 准备
        validRegisterBody.setPassword("");

        // 执行
        String result = emailRegisterService.register(validRegisterBody);

        // 验证
        assertEquals("用户密码不能为空", result);
        verify(userService, never()).registerUser(any(SysUser.class));
    }

    @Test
    void testRegister_PasswordTooShort() {
        // 准备
        validRegisterBody.setPassword("123");

        // 执行
        String result = emailRegisterService.register(validRegisterBody);

        // 验证
        assertEquals("密码长度必须在6到20个字符之间", result);
        verify(userService, never()).registerUser(any(SysUser.class));
    }

    @Test
    void testRegister_PasswordTooLong() {
        // 准备
        validRegisterBody.setPassword("123456789012345678901");

        // 执行
        String result = emailRegisterService.register(validRegisterBody);

        // 验证
        assertEquals("密码长度必须在6到20个字符之间", result);
        verify(userService, never()).registerUser(any(SysUser.class));
    }

    @Test
    void testRegister_EmailAlreadyExists() {
        // 准备
        when(configService.selectCaptchaEnabled()).thenReturn(false);
        when(userService.checkEmailUnique(any(SysUser.class))).thenReturn(false);

        // 执行
        String result = emailRegisterService.register(validRegisterBody);

        // 验证
        assertEquals("保存用户'test@example.com'失败，注册邮箱已存在", result);
        verify(userService, never()).registerUser(any(SysUser.class));
    }

    @Test
    void testRegister_RegistrationFailed() {
        // 准备
        when(configService.selectCaptchaEnabled()).thenReturn(false);
        when(userService.checkEmailUnique(any(SysUser.class))).thenReturn(true);
        when(userService.checkUserNameUnique(any(SysUser.class))).thenReturn(true);
        when(userService.registerUser(any(SysUser.class))).thenReturn(false);

        // 执行
        String result = emailRegisterService.register(validRegisterBody);

        // 验证
        assertEquals("注册失败,请联系系统管理人员", result);
    }

    @Test
    void testSendEmailVerifyCode_Success() {
        // 准备
        String email = "test@example.com";
        String verifyType = "register";
        when(userService.checkEmailUnique(any(SysUser.class))).thenReturn(true);

        // 执行
        String result = emailRegisterService.sendEmailVerifyCode(email, verifyType);

        // 验证
        assertEquals("", result);
        verify(emailVerifyMapper).insertSysEmailVerify(any(SysEmailVerify.class));
    }

    @Test
    void testSendEmailVerifyCode_EmailEmpty() {
        // 执行
        String result = emailRegisterService.sendEmailVerifyCode("", "register");

        // 验证
        assertEquals("邮箱地址不能为空", result);
        verify(emailVerifyMapper, never()).insertSysEmailVerify(any(SysEmailVerify.class));
    }

    @Test
    void testSendEmailVerifyCode_InvalidEmail() {
        // 执行
        String result = emailRegisterService.sendEmailVerifyCode("invalid-email", "register");

        // 验证
        assertEquals("邮箱格式不正确", result);
        verify(emailVerifyMapper, never()).insertSysEmailVerify(any(SysEmailVerify.class));
    }

    @Test
    void testSendEmailVerifyCode_EmailAlreadyRegistered() {
        // 准备
        String email = "test@example.com";
        when(userService.checkEmailUnique(any(SysUser.class))).thenReturn(false);

        // 执行
        String result = emailRegisterService.sendEmailVerifyCode(email, "register");

        // 验证
        assertEquals("该邮箱已注册", result);
        verify(emailVerifyMapper, never()).insertSysEmailVerify(any(SysEmailVerify.class));
    }

    @Test
    void testVerifyEmailCode_Success() {
        // 准备
        String email = "test@example.com";
        String verifyCode = "123456";
        String verifyType = "register";
        
        SysEmailVerify emailVerify = new SysEmailVerify();
        emailVerify.setId(1L);
        emailVerify.setVerifyCode("123456");
        emailVerify.setUsed(0);
        emailVerify.setExpireTime(new Date(System.currentTimeMillis() + 60000)); // 1分钟后过期
        
        when(emailVerifyMapper.selectLatestValidVerifyCode(email, verifyType)).thenReturn(emailVerify);
        when(emailVerifyMapper.markVerifyCodeAsUsed(1L)).thenReturn(1);

        // 执行
        boolean result = emailRegisterService.verifyEmailCode(email, verifyCode, verifyType);

        // 验证
        assertTrue(result);
        verify(emailVerifyMapper).markVerifyCodeAsUsed(1L);
    }

    @Test
    void testVerifyEmailCode_EmailEmpty() {
        // 执行
        boolean result = emailRegisterService.verifyEmailCode("", "123456", "register");

        // 验证
        assertFalse(result);
        verify(emailVerifyMapper, never()).markVerifyCodeAsUsed(anyLong());
    }

    @Test
    void testVerifyEmailCode_CodeEmpty() {
        // 执行
        boolean result = emailRegisterService.verifyEmailCode("test@example.com", "", "register");

        // 验证
        assertFalse(result);
        verify(emailVerifyMapper, never()).markVerifyCodeAsUsed(anyLong());
    }

    @Test
    void testVerifyEmailCode_NoValidCode() {
        // 准备
        when(emailVerifyMapper.selectLatestValidVerifyCode("test@example.com", "register")).thenReturn(null);

        // 执行
        boolean result = emailRegisterService.verifyEmailCode("test@example.com", "123456", "register");

        // 验证
        assertFalse(result);
        verify(emailVerifyMapper, never()).markVerifyCodeAsUsed(anyLong());
    }

    @Test
    void testVerifyEmailCode_WrongCode() {
        // 准备
        SysEmailVerify emailVerify = new SysEmailVerify();
        emailVerify.setId(1L);
        emailVerify.setVerifyCode("654321"); // 错误的验证码
        emailVerify.setUsed(0);
        emailVerify.setExpireTime(new Date(System.currentTimeMillis() + 60000));
        
        when(emailVerifyMapper.selectLatestValidVerifyCode("test@example.com", "register")).thenReturn(emailVerify);

        // 执行
        boolean result = emailRegisterService.verifyEmailCode("test@example.com", "123456", "register");

        // 验证
        assertFalse(result);
        verify(emailVerifyMapper, never()).markVerifyCodeAsUsed(anyLong());
    }

    @Test
    void testIsEmailRegistered_True() {
        // 准备
        when(userService.checkEmailUnique(any(SysUser.class))).thenReturn(false);

        // 执行
        boolean result = emailRegisterService.isEmailRegistered("test@example.com");

        // 验证
        assertTrue(result);
    }

    @Test
    void testIsEmailRegistered_False() {
        // 准备
        when(userService.checkEmailUnique(any(SysUser.class))).thenReturn(true);

        // 执行
        boolean result = emailRegisterService.isEmailRegistered("test@example.com");

        // 验证
        assertFalse(result);
    }

    @Test
    void testIsEmailRegistered_EmptyEmail() {
        // 执行
        boolean result = emailRegisterService.isEmailRegistered("");

        // 验证
        assertFalse(result);
    }
} 