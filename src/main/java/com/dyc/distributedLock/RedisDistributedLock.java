package com.dyc.distributedLock;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;


/**
 * 基于Redis的分布式锁的实现
 */
@Component
public class RedisDistributedLock extends AbstractDistributedLock {

    private final Logger logger = LoggerFactory.getLogger(RedisDistributedLock.class);

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

    public RedisDistributedLock(RedisTemplate<String, Object> redisTemplate) {
        super();
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean lock(String key, String requestId, long expire, int retryTimes, long sleepMillis) {
        boolean result = setRedis(key, requestId, expire);
        // 如果获取锁失败，按照传入的重试次数进行重试
        while ((!result) && retryTimes-- > 0) {
            try {
                logger.debug("lock failed, retrying..." + retryTimes);
                Thread.sleep(sleepMillis);
            } catch (InterruptedException e) {
                return false;
            }
            result = setRedis(key, requestId, expire);
        }
        return result;
    }

    private boolean setRedis(String key, String requestId, long expire) {
        try {
            //value 添加reqeustID
            return redisTemplate.execute((RedisCallback<Boolean>) connection -> connection.set(key.getBytes(), requestId.getBytes(),
                    Expiration.from(expire, TimeUnit.MILLISECONDS),
                    RedisStringCommands.SetOption.ifAbsent())).booleanValue();
        } catch (Exception e) {
            logger.error("set redis occured an exception", e);
        }
        return false;
    }


    @Override
    public boolean releaseLock(String key, String requestId) {
        return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            Boolean result = connection.eval(UNLOCK_LUA.getBytes(), ReturnType.BOOLEAN, 1, key.getBytes(), requestId.getBytes());
            return result.booleanValue();
        });
    }
}