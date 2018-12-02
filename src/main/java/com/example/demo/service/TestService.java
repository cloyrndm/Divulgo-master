package com.example.demo.service;

import com.example.demo.entity.Test;
import com.example.demo.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Cloie Andrea on 16/11/2018.
 */

@Service
public class TestService {

    @Autowired
    TestRepository testRepository;

    public void save(Test test){
        testRepository.save(test);
    }
}
