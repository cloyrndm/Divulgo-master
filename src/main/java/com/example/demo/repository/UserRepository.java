package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Katrina on 2/3/2019.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findAll();

    User findByUsername(String username);
}
