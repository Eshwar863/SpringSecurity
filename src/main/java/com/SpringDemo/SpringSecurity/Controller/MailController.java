package com.SpringDemo.SpringSecurity.Controller;

import com.SpringDemo.SpringSecurity.Dto.ForgotPassword;
import com.SpringDemo.SpringSecurity.Service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/mail")
public class MailController {

    @Autowired
    MailService mailService;

    @PostMapping("verifymail/{email}")
    private ResponseEntity<?> mail(@PathVariable (name = "email")String email) {
        return new ResponseEntity<>( mailService.SendOtp(email), HttpStatus.OK);
    }
    @PostMapping("valid/{otp}/{username}")
    private ResponseEntity<?> valid(@PathVariable String otp, @PathVariable String username) {
        return new ResponseEntity<>( mailService.ValidateOtp(otp,username), HttpStatus.OK);
    }

    @PostMapping("forgotpassword")
    private ResponseEntity<?> ForgotPassword(@RequestBody ForgotPassword forgotPassword) {
        ResponseEntity<?> mail = mailService.forgotpassword(forgotPassword);
       return new ResponseEntity<>(mail,HttpStatus.OK);
    }


}
