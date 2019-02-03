package com.example.demo.service;

import com.example.demo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Katrina on 9/27/2018.
 */
@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;


    public Users findUserByUsername(String username, String password) {

        return usersRepository.findByUsernameAndPassword(username,password);
    }

    public void saveUser(Users user) {
        usersRepository.save(user);
    }

    public Users findUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }
}
