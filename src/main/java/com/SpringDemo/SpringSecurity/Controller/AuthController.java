package com.SpringDemo.SpringSecurity.Controller;

import com.SpringDemo.SpringSecurity.Dto.LoginDto;

import com.SpringDemo.SpringSecurity.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {
final
UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("login")
    public String login(@RequestBody LoginDto user) {

    return userService.Verify(user);
}

}
