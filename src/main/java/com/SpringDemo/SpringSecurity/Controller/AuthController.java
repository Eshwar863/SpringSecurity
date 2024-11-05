package com.SpringDemo.SpringSecurity.Controller;

import com.SpringDemo.SpringSecurity.Dto.LoginDto;
import com.SpringDemo.SpringSecurity.Entity.User;
import com.SpringDemo.SpringSecurity.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {
@Autowired
UserService userService;
@PostMapping("login")
    public String login(@RequestBody LoginDto user) {
    return userService.Verify(user);
}

}
