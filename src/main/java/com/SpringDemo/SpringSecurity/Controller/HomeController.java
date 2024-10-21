package com.SpringDemo.SpringSecurity.Controller;

import com.SpringDemo.SpringSecurity.Entity.User;
import com.SpringDemo.SpringSecurity.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HomeController {
    @Autowired
    UserService userService;
    @GetMapping("home")
    public String home() {
        return "Welcome to Spring Security";
    }
    @GetMapping("alive")
    public String alive() {
     return userService.Servicealive();
    }
    @GetMapping("users")
    public List<User> AllUsers() {
    return userService.getAllusers();
    }
    @PostMapping("register")
    public User register(@RequestBody User user) {

        return userService.register(user);
    }
}
