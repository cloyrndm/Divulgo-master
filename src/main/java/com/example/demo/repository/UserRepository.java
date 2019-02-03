package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Cloie Andrea on 03/02/2019.
 */
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUserId(Long id);
}
