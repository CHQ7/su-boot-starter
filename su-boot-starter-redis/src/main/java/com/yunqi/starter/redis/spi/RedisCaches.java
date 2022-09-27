package com.yunqi.starter.redis.spi;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * Redis缓存工具类
 * Created by @author CHQ on 2022/8/22
 */
public class RedisCaches {

    private volatile static RedisTemplate<String, Object> redisTemplate;

    public static void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        RedisCaches.redisTemplate = redisTemplate;
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return RedisCaches.redisTemplate;
    }

    /* -------------------key相关操作--------------------- */


    /**
     * 设置有效时间
     *
     * @param key       缓存键值
     * @param timeout   超时时间(秒)
     * @return          boolean
     */
    public static boolean expire(final String key,final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key       缓存键值
     * @param timeout   超时时间
     * @param unit      时间单位
     * @return          boolean
     */
    public static boolean expire(final String key, final long timeout, final TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key   缓存键值
     * @return      有效时间
     */
    public static long getExpire(final String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 判断key是否存在
     *
     * @param key 缓存键值
     * @return    boolean
     */
    public static boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }


    /**
     * 删除单个对象
     * @param key   缓存键值
     * @return      Boolean
     */
    public boolean deleteObject(final String key){
        return redisTemplate.delete(key);
    }

    /**
     * 删除缓存
     *
     * @param key 缓存键值,可以传一个值或多个
     */
    @SuppressWarnings("unchecked")
    public static void del(final String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(key));
            }
        }
    }

    // ============================String=============================

    /**
     * 获得缓存的基本对象
     *
     * @param key   缓存键值
     * @return      缓存键值对应的数据
     */
    public static Object getCacheObject(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入，Integer、String、实体类等
     *
     * @param key   缓存键值
     * @param value 缓存的值
     */
    public static void setCacheObject(final String key,final Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key       缓存键值
     * @param value     缓存的值
     * @param timeout   时间(秒)
     */
    public static void setCacheObject(final String key, final Object value, final Integer timeout) {
        setCacheObject(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key       缓存键值
     * @param value     缓存的值
     * @param timeout   时间
     * @param timeUnit  时间颗粒度
     */
    public static void setCacheObject(final String key, final Object value, final Integer timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 递增
     *
     * @param key   缓存键值
     * @param delta 要增加几(大于0)
     */
    public static long incr(final String key,final long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key   缓存键值
     * @param delta 要减少几(小于0)
     */
    public static long decr(final String key,final long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    // ================================Map=================================


    /**
     * 获取Hash中的数据
     *
     * @param key   缓存键值
     * @param item  Hash键
     * @return      Hash中的对象
     */
    public static Object getCacheMapValue(final String key,final String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获得缓存的Map
     *
     * @param key   缓存键值
     * @return      对应的多个键值
     */
    public static Map<Object, Object> getCacheMap(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 设置缓存的Map
     *
     * @param key 缓存键值
     * @param map 对应多个键值
     */
    public static void setCacheMap(final String key,final Map<String, Object> map) {
        if (map != null) {
            redisTemplate.opsForHash().putAll(key, map);
        }
    }

    /**
     * 设置缓存的Map
     *
     * @param key       缓存键值
     * @param map       对应多个键值
     * @param timeout   有效时间(秒)
     * @return          boolean
     */
    public static boolean setCacheMap(final String key,final Map<String, Object> map,final long timeout) {
        try {
            setCacheMap(key, map);
            if (timeout > 0) {
                expire(key, timeout);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 往Hash中存入数据
     *
     * @param key       缓存键值
     * @param hashKey   map值的key
     * @param value     map值的值
     */
    public static void setCacheMapValue(final String key,final String hashKey,final Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key       缓存键值
     * @param hashKey   map值的key
     * @param value     map值的值
     * @param timeout   有效时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return          boolean
     */
    public static boolean setCacheMapValue(final String key,final String hashKey,final Object value,final long timeout) {
        try {
            setCacheMapValue(key, hashKey, value);
            if (timeout > 0) {
                expire(key, timeout);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除Hash中的数据
     *
     * @param key      缓存键值
     * @param hashKeys map值的key
     */
    public static void delCacheMapValue(final String key,final Object... hashKeys) {
        redisTemplate.opsForHash().delete(key, hashKeys);
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key       Redis键
     * @param hashKeys  Hash键集合
     * @return          Hash对象集合
     */
    public static List<Object> getMultiCacheMapValue(final String key, final Collection<Object> hashKeys) {
        return redisTemplate.opsForHash().multiGet(key, hashKeys);
    }


    /**
     * 判断hash表中是否有该项的值
     *
     * @param key      缓存键值
     * @param hashKeys map值的key
     * @return         boolean
     */
    public static boolean hHasKey(final String key, final String hashKeys) {
        return redisTemplate.opsForHash().hasKey(key, hashKeys);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key      缓存键值
     * @param hashKeys map值的key
     * @param by       要增加几(大于0)
     */
    public static double hincr(final String key,final String hashKeys,final double by) {
        return redisTemplate.opsForHash().increment(key, hashKeys, by);
    }

    /**
     * hash递减
     *
     * @param key       缓存键值
     * @param hashKeys  map值的key
     * @param by        要减少记(小于0)
     */
    public static double hdecr(final String key,final String hashKeys,final double by) {
        return redisTemplate.opsForHash().increment(key, hashKeys, -by);
    }

    // ============================set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 缓存键值
     */
    public static Set<Object> getCacheSet(final String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   缓存键值
     * @param value 值
     * @return      boolean
     */
    public static boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public static long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public static long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     */
    public static long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public static long setRemove(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // ===============================list=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   缓存键值
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return      获取list缓存的内容
     */
    public static List<Object> getCacheList(final String key,final long start,final long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 获取list缓存的内容
     *
     * @param key   缓存键值
     * @return      list缓存的内容
     */
    public static List<Object> getCacheList(final String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 获取list缓存的长度
     *
     * @param key   缓存键值
     * @return      list缓存的长度
     */
    public static long getCacheListSize(final String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 通过索引获取list缓存中的值
     *
     * @param key   缓存键值
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0 时，-1，表尾，-2倒数第二个元素，依次类推
     */
    public static Object getCacheListIndex(final String key,final long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 将list放入缓存
     *
     * @param key   缓存键值
     * @param value 值
     */
    public static boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     */
    public static boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 缓存List数据
     *
     * @param key       缓存的键值
     * @param dataList  待缓存的List数据
     * @return          缓存的对象
     */
    public static long lSet(final String key,final List<Object> dataList) {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return      boolean
     */
    public static boolean lSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return      boolean
     */
    public static boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return      移除的个数
     */
    public static long lRemove (String key,long count, Object value){
        try {
            return redisTemplate.opsForList().remove(key, count,value);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public static Collection<String> keys(final String pattern){
        return redisTemplate.keys(pattern);
    }

}
