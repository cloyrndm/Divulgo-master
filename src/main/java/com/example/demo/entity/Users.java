package com.example.demo.entity;

import javax.persistence.*;

/**
 * Created by Cloie Andrea on 30/11/2018.
 */
@Entity
@Table(name="user")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int id;
    private String username;
    private String password;
    private String email;

    public Users() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
