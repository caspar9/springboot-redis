package com.dyc.api;

import com.dyc.async.IAsyncService;
import com.dyc.distributedLock.DistributedLock;
import com.dyc.distributedLock.LockKeys;
import com.dyc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AsyncTestApi {

    @Autowired
    private IAsyncService testAsyncService;

    @GetMapping("/async")
    public String testRedis() {


        testAsyncService.asyncTask();

        return "SUCCESS";
    }
}
