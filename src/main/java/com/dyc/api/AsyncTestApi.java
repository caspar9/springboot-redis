package com.dyc.api;

import com.dyc.async.AsyncConfig;
import com.dyc.async.IAsyncService;
import com.dyc.async.UserAsyncTask;
import com.dyc.model.User;
import com.dyc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AsyncTestApi {

    @Autowired
    private IAsyncService testAsyncService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserAsyncTask userAsyncTask;

    @Autowired
    private AsyncConfig asyncConfig;


    @GetMapping("/asyncTask")
    public String asyncTask() {

        testAsyncService.asyncTask();

        return "SUCCESS";
    }


    @GetMapping("/asyncApi")
    public String testRasyncApiedis() throws InterruptedException {
        User u = userService.addUser();
        System.out.println(u.toString());



        userAsyncTask.asyncTask(u.getId());


//        for (int i = 0 ;i<10;i++){
//            userAsyncTask.asyncTask(u.getId());
//        }

        return "SUCCESS";
    }
}
