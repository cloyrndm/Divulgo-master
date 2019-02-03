package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Katrina on 2/3/2019.
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    public void save(User user){

        userRepository.save(user);
    }


    public List<User> getAll(){

        return userRepository.findAll();
    }


    public User findByUsernameandPassword (String username, String password) {

        return userRepository.findByUsernameandPassword(username,password);
    }
}
