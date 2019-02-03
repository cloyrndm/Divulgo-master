package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Katrina on 9/27/2018.
 */
public interface UsersRepository extends JpaRepository<Users, Integer> {
    Users findByUsernameAndPassword(String username,String password);
    Users findByEmail(String email);

}
