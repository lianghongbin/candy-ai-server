package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.common.core.domain.entity.SysUserOauth;

/**
 * 用户第三方登录关联Mapper接口
 * 
 * @author ruoyi
 */
public interface SysUserOauthMapper 
{
    /**
     * 查询用户第三方登录关联
     * 
     * @param id 用户第三方登录关联主键
     * @return 用户第三方登录关联
     */
    public SysUserOauth selectSysUserOauthById(Long id);

    /**
     * 查询用户第三方登录关联列表
     * 
     * @param sysUserOauth 用户第三方登录关联
     * @return 用户第三方登录关联集合
     */
    public List<SysUserOauth> selectSysUserOauthList(SysUserOauth sysUserOauth);

    /**
     * 根据用户ID和第三方类型查询关联信息
     * 
     * @param userId 用户ID
     * @param oauthType 第三方类型
     * @return 用户第三方登录关联
     */
    public SysUserOauth selectSysUserOauthByUserIdAndType(Long userId, String oauthType);

    /**
     * 根据第三方平台用户ID和类型查询关联信息
     * 
     * @param oauthUserId 第三方平台用户ID
     * @param oauthType 第三方类型
     * @return 用户第三方登录关联
     */
    public SysUserOauth selectSysUserOauthByOauthUserIdAndType(String oauthUserId, String oauthType);

    /**
     * 新增用户第三方登录关联
     * 
     * @param sysUserOauth 用户第三方登录关联
     * @return 结果
     */
    public int insertSysUserOauth(SysUserOauth sysUserOauth);

    /**
     * 修改用户第三方登录关联
     * 
     * @param sysUserOauth 用户第三方登录关联
     * @return 结果
     */
    public int updateSysUserOauth(SysUserOauth sysUserOauth);

    /**
     * 删除用户第三方登录关联
     * 
     * @param id 用户第三方登录关联主键
     * @return 结果
     */
    public int deleteSysUserOauthById(Long id);

    /**
     * 批量删除用户第三方登录关联
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysUserOauthByIds(Long[] ids);
} 