package com.SpringDemo.SpringSecurity.Service;

import com.SpringDemo.SpringSecurity.Dto.ForgotPassword;
import com.SpringDemo.SpringSecurity.Entity.Otp;
import com.SpringDemo.SpringSecurity.Entity.User;
import com.SpringDemo.SpringSecurity.Repo.OtpRepo;
import com.SpringDemo.SpringSecurity.Repo.UserRepo;
import org.aspectj.weaver.patterns.IToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class MailService {
    private static final int OTP_LENGTH = 6;
    static final int OTP_EXPIRY_MINUTES = 5;
    final
    JavaMailSender mailSender;
    final
    UserService userService;
    final
    UserRepo userRepo;
    final
    OtpRepo otpRepo;
    private final JwtService jwtService;
    @Value("${spring.mail.username}")
    private String fromEmail;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    public MailService(JavaMailSender mailSender, UserService userService, UserRepo userRepo, OtpRepo otpRepo, JwtService jwtService) {
        this.mailSender = mailSender;
        this.userService = userService;
        this.userRepo = userRepo;
        this.otpRepo = otpRepo;
        this.jwtService = jwtService;
    }

    public ResponseEntity<?> SendOtp(String email) {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            return new ResponseEntity<>("Mail Not Found", HttpStatus.NOT_FOUND);
        }
        Otp otpexist = otpRepo.findByUser(user);
        if (otpexist!= null) {
            SimpleMailMessage message1 = new SimpleMailMessage();
            message1.setFrom(fromEmail);
            message1.setTo(email);
            message1.setSubject("Otp For Forgot Password");
            message1.setText(String.format("Hello! %s,\n" +
                    "Your otp for Forgot Password \n" +
                    "OTP : '%s' ,\n" +
                    "expires at %s",user.getUserName(), otpexist.getOtp(),otpexist.getOtpTime()));
            mailSender.send(message1);

            return new ResponseEntity<>("Otp Already Sent Please try After Some Time", HttpStatus.OK);
        }

        String otp = generateOtp();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(email);
        message.setSubject("Otp For Forgot Password");
        message.setText(String.format("Hello! %s,\n"+
                "Your otp for Forgot Password is : '%s'\n"+
                "expires in 5 mins.",user.getUserName(),otp));
        mailSender.send(message);
        otp(user,otp);
        return ResponseEntity.ok("Otp Sent to "+email);
    }

    public void otp(User users, String otp) {
        Otp otpEntity = new Otp();
        otpEntity.setOtp(otp);
        otpEntity.setUser(users);
        otpEntity.setOtpTime(LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES));
        otpRepo.save(otpEntity);
    }

    public String generateOtp() {
        Random random = new Random();
        int otpNumber = random.nextInt((int) Math.pow(10, OTP_LENGTH));
        return String.format("%0" + OTP_LENGTH + "d", otpNumber);
    }

    public ResponseEntity<?> ValidateOtp(String otp,String username) {
        Otp otpEntity = otpRepo.findByOtpAndUser(otp,username);
        if (otpEntity == null) {
            return new ResponseEntity<>("Invalid Otp", HttpStatus.NOT_FOUND);
        }
        if (otpEntity.getOtpTime().isBefore(LocalDateTime.now())) {
            otpRepo.delete(otpEntity);
            return new ResponseEntity<>("Otp Expired", HttpStatus.BAD_REQUEST);
        }
        if (otpEntity.getOtp().equals(otp)) {
            otpRepo.delete(otpEntity);
            if (jwtService!= null){
                String token = jwtService.generateToken(username);
                return new ResponseEntity<>(token, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("Unable to Process", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return null;
    }






    private User retriveLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated())
            throw new BadCredentialsException("Bad Credentials login ");
        String username = authentication.getName();
        System.out.println(STR."In Logged In User \{username}");
        User user = userRepo.findByUserName(username);
        if(user == null){
            throw new UsernameNotFoundException("User Not Found");
        }
        return user;
    }

    public ResponseEntity<?> forgotpassword(ForgotPassword forgotPassword) {
        User user = retriveLoggedInUser();
        if (user == null) {
            return new ResponseEntity<>("Unable to process", HttpStatus.GATEWAY_TIMEOUT);
        }
        if (forgotPassword.getPassword().equals(forgotPassword.getReenterpassword())) {
            user.setPassword(encoder.encode(forgotPassword.getPassword()));
            userRepo.save(user);
            return new ResponseEntity<>("Password Changed Successfully", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Passwords don't match", HttpStatus.BAD_REQUEST);
        }
    }
}
