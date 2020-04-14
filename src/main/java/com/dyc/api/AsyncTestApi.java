package com.dyc.api;

import com.dyc.async.IAsyncService;
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
