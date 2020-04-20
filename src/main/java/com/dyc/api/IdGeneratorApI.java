package com.dyc.api;

import com.dyc.DistributedId.SnowflakeIdGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IdGeneratorApI {

    @GetMapping("/genid")
    public String testRedis() {

        String id = String.valueOf(SnowflakeIdGenerator.INSTANCE.nextId());

        return id;
    }
}
