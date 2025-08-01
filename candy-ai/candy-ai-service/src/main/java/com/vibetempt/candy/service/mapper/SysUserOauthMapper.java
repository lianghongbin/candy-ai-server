package com.vibetempt.candy.service.mapper;

import com.vibetempt.candy.domain.entity.SysUserOauth;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户第三方登录关联Mapper接口
 * 
 * @author candy
 */
@Mapper
public interface SysUserOauthMapper {
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
     * 根据用户ID和第三方类型查询关联
     * 
     * @param userId 用户ID
     * @param oauthType 第三方类型
     * @return 用户第三方登录关联
     */
    public SysUserOauth selectSysUserOauthByUserIdAndType(@Param("userId") Long userId, @Param("oauthType") String oauthType);

    /**
     * 根据第三方用户ID和类型查询关联
     * 
     * @param oauthUserId 第三方用户ID
     * @param oauthType 第三方类型
     * @return 用户第三方登录关联
     */
    public SysUserOauth selectSysUserOauthByOauthUserIdAndType(@Param("oauthUserId") String oauthUserId, @Param("oauthType") String oauthType);

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