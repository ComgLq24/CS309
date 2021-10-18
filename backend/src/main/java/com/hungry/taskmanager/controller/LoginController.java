package com.hungry.taskmanager.controller;

import com.hungry.taskmanager.dao.UserMapper;
import com.hungry.taskmanager.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.hungry.taskmanager.dao.UserMapper;

@RestController
public class LoginController {

    @RequestMapping("/login")
    public String authLogin(String username, String pwd){
        return "hello";
    }

    @RequestMapping("/user")
    public User getUser(User user){
        return user;
    }

}
