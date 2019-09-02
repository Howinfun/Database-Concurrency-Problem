package com.hyf.utils;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author Howinfun
 * @desc Redisson工具类
 * @date 2019/9/2
 */
public class RedissonUtil {

    private static RedissonClient redissonClient;

    private RedissonUtil(){
        // 构造redisson实现分布式锁必要的Config
        Config config = new Config();
        config.useSingleServer().setAddress("redis://47.107.129.42:6379").setPassword("HowinfunRedis").setDatabase(1);
        // 构造RedissonClient
        redissonClient = Redisson.create(config);
    }

    public static final RedissonUtil INSTANCE = new RedissonUtil();

    /**
     * 设定锁定资源名称，返回锁
     * @param name
     * @return
     */
    public RLock getLock(String name){
        return redissonClient.getLock(name);
    }
}
