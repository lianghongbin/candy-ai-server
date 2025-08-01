package com.ruoyi.common.core.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 可配置的 Redis 缓存工具类
 * 支持在开发环境禁用缓存，在生产环境启用缓存
 *
 * @author candy
 */
@Component
public class ConfigurableRedisCache {

    @Autowired
    private RedisCache redisCache;

    @Value("${candy.cache.enabled:true}")
    private boolean cacheEnabled;

    /**
     * 缓存基本的对象，Integer、String、实体类等
     */
    public <T> void setCacheObject(final String key, final T value) {
        if (cacheEnabled) {
            redisCache.setCacheObject(key, value);
        }
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     */
    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit) {
        if (cacheEnabled) {
            redisCache.setCacheObject(key, value, timeout, timeUnit);
        }
    }

    /**
     * 设置有效时间
     */
    public boolean expire(final String key, final long timeout) {
        if (cacheEnabled) {
            return redisCache.expire(key, timeout);
        }
        return false;
    }

    /**
     * 设置有效时间
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit) {
        if (cacheEnabled) {
            return redisCache.expire(key, timeout, unit);
        }
        return false;
    }

    /**
     * 获取有效时间
     */
    public long getExpire(final String key) {
        if (cacheEnabled) {
            return redisCache.getExpire(key);
        }
        return -1;
    }

    /**
     * 判断 key是否存在
     */
    public Boolean hasKey(String key) {
        if (cacheEnabled) {
            return redisCache.hasKey(key);
        }
        return false;
    }

    /**
     * 获得缓存的基本对象
     */
    public <T> T getCacheObject(final String key) {
        if (cacheEnabled) {
            return redisCache.getCacheObject(key);
        }
        return null;
    }

    /**
     * 删除单个对象
     */
    public boolean deleteObject(final String key) {
        if (cacheEnabled) {
            return redisCache.deleteObject(key);
        }
        return false;
    }

    /**
     * 删除集合对象
     */
    public boolean deleteObject(final Collection collection) {
        if (cacheEnabled) {
            return redisCache.deleteObject(collection);
        }
        return false;
    }

    /**
     * 缓存List数据
     */
    public <T> long setCacheList(final String key, final List<T> dataList) {
        if (cacheEnabled) {
            return redisCache.setCacheList(key, dataList);
        }
        return 0;
    }

    /**
     * 获得缓存的list对象
     */
    public <T> List<T> getCacheList(final String key) {
        if (cacheEnabled) {
            return redisCache.getCacheList(key);
        }
        return null;
    }

    /**
     * 缓存Set
     */
    public <T> BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> dataSet) {
        if (cacheEnabled) {
            return redisCache.setCacheSet(key, dataSet);
        }
        return null;
    }

    /**
     * 获得缓存的set
     */
    public <T> Set<T> getCacheSet(final String key) {
        if (cacheEnabled) {
            return redisCache.getCacheSet(key);
        }
        return null;
    }

    /**
     * 缓存Map
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap) {
        if (cacheEnabled) {
            redisCache.setCacheMap(key, dataMap);
        }
    }

    /**
     * 获得缓存的Map
     */
    public <T> Map<String, T> getCacheMap(final String key) {
        if (cacheEnabled) {
            return redisCache.getCacheMap(key);
        }
        return null;
    }

    /**
     * 往Hash中存入数据
     */
    public <T> void setCacheMapValue(final String key, final String hKey, final T value) {
        if (cacheEnabled) {
            redisCache.setCacheMapValue(key, hKey, value);
        }
    }

    /**
     * 获取Hash中的数据
     */
    public <T> T getCacheMapValue(final String key, final String hKey) {
        if (cacheEnabled) {
            return redisCache.getCacheMapValue(key, hKey);
        }
        return null;
    }

    /**
     * 获取多个Hash中的数据
     */
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys) {
        if (cacheEnabled) {
            return redisCache.getMultiCacheMapValue(key, hKeys);
        }
        return null;
    }

    /**
     * 删除Hash中的某条数据
     */
    public boolean deleteCacheMapValue(final String key, final String hKey) {
        if (cacheEnabled) {
            return redisCache.deleteCacheMapValue(key, hKey);
        }
        return false;
    }

    /**
     * 获得缓存的基本对象列表
     */
    public Collection<String> keys(final String pattern) {
        if (cacheEnabled) {
            return redisCache.keys(pattern);
        }
        return null;
    }

    /**
     * 检查缓存是否启用
     */
    public boolean isCacheEnabled() {
        return cacheEnabled;
    }
} 