package com.dyc.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Future;

@Service
public class TestAsyncService implements IAsyncService {

    @Autowired
    private TestService testService;

    @Override
//    @Async
    @Async("asyncTaskExecutor")
    public void asyncTask() {
        long startTime = System.currentTimeMillis();
        try {
            //模拟耗时
            Thread.sleep(10000);
            testService.doit();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + "asyncTask()，耗时：" + (endTime - startTime));
    }

    @Override
    @Async("asyncTaskExecutor")
    public Future<String> asyncTask(String s) {
        long startTime = System.currentTimeMillis();
        try {
            //模拟耗时
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + "：Future<String> asyncTask(String s)，耗时：" + (endTime - startTime));
        return AsyncResult.forValue(s);
    }

    @Override
    @Async("asyncTaskExecutor")
    @Transactional
    public void asyncTaskForTransaction(Boolean exFlag) {
        //写入数据库操作
        System.out.println("写入数据库");

        if (exFlag) {
            //模拟异常
            throw new RuntimeException("模拟异常");
        }
    }
}
