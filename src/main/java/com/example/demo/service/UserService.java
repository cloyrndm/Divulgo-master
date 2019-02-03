package com.example.demo.service;

import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Katrina on 9/27/2018.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public Users findUserByUsername(String username, String password) {

        return userRepository.findByUsernameAndPassword(username,password);
    }

    public void saveUser(Users user) {
        userRepository.save(user);
    }

    public Users findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
