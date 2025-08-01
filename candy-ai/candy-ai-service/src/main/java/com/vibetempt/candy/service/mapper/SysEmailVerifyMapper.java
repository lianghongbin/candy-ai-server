package com.vibetempt.candy.service.mapper;

import com.vibetempt.candy.domain.entity.SysEmailVerify;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 邮箱验证码Mapper接口
 * 
 * @author candy
 */
@Mapper
public interface SysEmailVerifyMapper {
    /**
     * 查询邮箱验证码
     * 
     * @param id 邮箱验证码主键
     * @return 邮箱验证码
     */
    public SysEmailVerify selectSysEmailVerifyById(Long id);

    /**
     * 查询邮箱验证码列表
     * 
     * @param sysEmailVerify 邮箱验证码
     * @return 邮箱验证码集合
     */
    public List<SysEmailVerify> selectSysEmailVerifyList(SysEmailVerify sysEmailVerify);

    /**
     * 查询最新的有效验证码
     * 
     * @param email 邮箱地址
     * @param verifyType 验证类型
     * @return 邮箱验证码
     */
    public SysEmailVerify selectLatestValidVerifyCode(@Param("email") String email, @Param("verifyType") String verifyType);

    /**
     * 标记验证码为已使用
     * 
     * @param id 验证码ID
     * @return 结果
     */
    public int markVerifyCodeAsUsed(Long id);

    /**
     * 清理过期的验证码
     * 
     * @return 清理数量
     */
    public int cleanExpiredVerifyCodes();

    /**
     * 新增邮箱验证码
     * 
     * @param sysEmailVerify 邮箱验证码
     * @return 结果
     */
    public int insertSysEmailVerify(SysEmailVerify sysEmailVerify);

    /**
     * 修改邮箱验证码
     * 
     * @param sysEmailVerify 邮箱验证码
     * @return 结果
     */
    public int updateSysEmailVerify(SysEmailVerify sysEmailVerify);

    /**
     * 删除邮箱验证码
     * 
     * @param id 邮箱验证码主键
     * @return 结果
     */
    public int deleteSysEmailVerifyById(Long id);

    /**
     * 批量删除邮箱验证码
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysEmailVerifyByIds(Long[] ids);
} 