package com.dyc.service;

import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class RedisLock {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public static final String UNLOCK_LUA;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
        sb.append("then ");
        sb.append("    return redis.call(\"del\",KEYS[1]) ");
        sb.append("else ");
        sb.append("    return 0 ");
        sb.append("end ");
        UNLOCK_LUA = sb.toString();
    }

    public RedisLock(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    //加锁   key和value默认一致
    public boolean lock(final String key, final long expirTime) {
        return redisTemplate.execute((RedisCallback<Boolean>) connection -> connection.set(key.getBytes(), key.getBytes(),
                Expiration.from(expirTime, TimeUnit.MILLISECONDS),
                RedisStringCommands.SetOption.ifAbsent())).booleanValue();
    }

    //释放锁  key和value默认一致
    public boolean releaseLock(String key) {
        return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            Boolean result = connection.eval(UNLOCK_LUA.getBytes(), ReturnType.BOOLEAN, 1, key.getBytes(), key.getBytes());
            return result.booleanValue();
        });
    }
}
