package com.dyc.async;

import com.dyc.model.User;
import com.dyc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAsyncTask {

    @Autowired
    private UserRepository userRepository;

    @Async("asyncTaskExecutor")
    public void asyncTask(String userId) throws InterruptedException {
        Optional<User> u = userRepository.findById(userId);
        Thread.sleep(10000);
        System.out.println(u.isPresent() ? u.toString() : "未查到");
    }
}
