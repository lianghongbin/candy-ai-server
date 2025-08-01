package com.vibetempt.candy.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vibetempt.candy.common.query.QueryCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 通用 Mapper 接口
 * 
 * @author vibetempt
 */
public interface BaseMapper<T> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {
    
    /**
     * 根据条件查询列表
     */
    List<T> selectListByCondition(@Param("conditions") Map<String, Object> conditions);
    
    /**
     * 根据条件查询总数
     */
    Long selectCountByCondition(@Param("conditions") Map<String, Object> conditions);
    
    /**
     * 根据条件查询列表（带分页）
     */
    List<T> selectPageByCondition(@Param("conditions") Map<String, Object> conditions);
} 