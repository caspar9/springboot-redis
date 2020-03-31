package com.dyc.api;

import com.dyc.distributedLock.DistributedLock;
import com.dyc.distributedLock.LockKeys;
import com.dyc.model.User;
import com.dyc.service.RedisLock;
import com.dyc.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class UserApi {

    @Autowired
    private RedisService redisService;

    @Autowired
    private DistributedLock redisDistributedLock;

    @GetMapping("/redis")
    public String testRedis() {

        User user = new User();
        user.setName("liubang");
        user.setAge("88");

        //redisTemplate.opsForValue().set("dyc",user);
        //User u = (User)redisTemplate.opsForValue().get("dyc");

        if (redisDistributedLock.lock(LockKeys.MERCHANT_SYNC.name())) {

            //todo
            redisService.set("dyc", user);
            User u = redisService.get("dyc", User.class);

           // System.out.println(redisDistributedLock.releaseLock(lockKey));
            return "ok:" + u.toString();
        } else {
            return "Faild:未get到锁。";
        }
    }
}