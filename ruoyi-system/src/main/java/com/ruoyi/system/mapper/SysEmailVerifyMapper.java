package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.common.core.domain.entity.SysEmailVerify;

/**
 * 邮箱验证码Mapper接口
 * 
 * @author ruoyi
 */
public interface SysEmailVerifyMapper 
{
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
     * 根据邮箱和验证类型查询最新的有效验证码
     * 
     * @param email 邮箱地址
     * @param verifyType 验证类型
     * @return 邮箱验证码
     */
    public SysEmailVerify selectLatestValidVerifyCode(String email, String verifyType);

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
     * @return 清理的记录数
     */
    public int cleanExpiredVerifyCodes();
} 