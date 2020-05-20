package com.dyc.api;

import com.dyc.distributedLock.DistributedLock;
import com.dyc.distributedLock.LockKeys;
import com.dyc.model.User;
import com.dyc.service.RedisLock;
import com.dyc.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class UserApi {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private DistributedLock redisDistributedLock;

    @GetMapping("/redis")
    public String testRedis() {
        //获取锁
        String requestId = UUID.randomUUID().toString();
        if (!redisDistributedLock.lock(LockKeys.MERCHANT_SYNC.name(), requestId)) {
            return "Faild:未get到锁。";
        }

        //todo
        User user = new User();
        user.setName("liubang");
        user.setAge("88");
        redisTemplate.opsForValue().set("dyc", user, 3, TimeUnit.MILLISECONDS);
        User u = (User) redisTemplate.opsForValue().get("dyc");

        //释放锁
        redisDistributedLock.releaseLock(LockKeys.MERCHANT_SYNC.name(), requestId);

        return "ok:" + u.toString();
    }
}