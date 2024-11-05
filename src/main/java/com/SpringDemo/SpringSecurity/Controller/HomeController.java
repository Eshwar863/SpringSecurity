package com.SpringDemo.SpringSecurity.Controller;

import com.SpringDemo.SpringSecurity.Dto.AllUsers;
import com.SpringDemo.SpringSecurity.Dto.RegisterDto;
import com.SpringDemo.SpringSecurity.Dto.UpdateUserDetails;
import com.SpringDemo.SpringSecurity.Entity.User;
import com.SpringDemo.SpringSecurity.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<AllUsers>> AllUsers() {
    return userService.getAllusers();
    }
    @PostMapping("register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }
    @PutMapping("updateuserdetails")
    public ResponseEntity<?> update(@RequestBody UpdateUserDetails userDetails) {
            if (userDetails == null){
                return new ResponseEntity<>("Enter UserDetails", HttpStatus.BAD_GATEWAY);
            }
            ResponseEntity<?> updateUserDetails =userService.updateUserDetails(userDetails);
        return new ResponseEntity<>(updateUserDetails,HttpStatus.OK);
    }
}
