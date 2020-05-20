package com.dyc.service;

import com.dyc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dyc.model.User;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User addUser() {
        User u = new User();
        u.setId(UUID.randomUUID().toString());
        u.setAge("99");
        u.setName("刘邦");
        userRepository.save(u);
        return u;
    }
}
