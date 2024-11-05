package com.SpringDemo.SpringSecurity.Service;

import com.SpringDemo.SpringSecurity.Dto.AllUsers;
import com.SpringDemo.SpringSecurity.Dto.LoginDto;
import com.SpringDemo.SpringSecurity.Dto.UpdateUserDetails;
import com.SpringDemo.SpringSecurity.Entity.User;
import com.SpringDemo.SpringSecurity.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    public ResponseEntity<List<AllUsers>> getAllusers() {
        User user = retriveLoggedInUser();
        List<User> users = userRepo.findAll();
        return new ResponseEntity<>( users.stream()
                .map(alluser -> new AllUsers(
                    alluser.getId(),
                        alluser.getUserName(),
                        alluser.getEmail(),
                        alluser.getProfilepic(),
                        alluser.getDOB(),
                        alluser.getGender()
                )).collect(Collectors.toList()), HttpStatus.OK);
    }
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    public User register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        System.out.println(user.getUserName());
        return userRepo.save(user);
    }

    public String Verify(LoginDto user) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUserName());
        }
        else
            return"Session Expired";
    }

    public String Servicealive() {
        System.out.println("Server is alive");
        return "Alive";
    }

    public ResponseEntity<?> updateUserDetails(UpdateUserDetails userDetails) {
        User user = retriveLoggedInUser();
        if (user == null) {
            return new ResponseEntity<>("User Not Found", HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(new UpdateUserDetails(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getProfilepic(),
                user.getDOB(),
                user.getGender()),
                HttpStatus.OK);
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
}
