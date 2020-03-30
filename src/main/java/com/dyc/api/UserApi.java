package com.dyc.api;

import com.dyc.model.User;
import com.dyc.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApi {

    @Autowired
    private RedisService redisService;


    @GetMapping("/redis")
    public String testRedis() {

        User user = new User();
        user.setName("liubang");
        user.setAge("88");

        //redisTemplate.opsForValue().set("dyc",user);
        //User u = (User)redisTemplate.opsForValue().get("dyc");

        //System.out.println(redisTemplate.delete("lock"));

        if(redisService.lock("lock",5000))
        {

            //todo
            redisService.set("dyc", user);
            User u = redisService.get("dyc", User.class);


            redisService.unlock("lock");
            return "ok:" + u.toString();
        }else{
            return "Faild:未get到锁。";
        }
    }
}