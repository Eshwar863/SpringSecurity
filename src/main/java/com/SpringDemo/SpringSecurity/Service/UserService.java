package com.SpringDemo.SpringSecurity.Service;

import com.SpringDemo.SpringSecurity.Dto.UpdateUserDetails;
import com.SpringDemo.SpringSecurity.Entity.User;
import com.SpringDemo.SpringSecurity.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    public List<User> getAllusers() {
        return userRepo.findAll();
    }
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    public User register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        System.out.println(user);
       return userRepo.save(user);
    }

    public String Verify(User user) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUserName());
        }
        else
            return "Session Expired";
    }

    public String Servicealive() {
        System.out.println("Server is alive");
        return "Alive";
    }

    public ResponseEntity<?> updateUserDetails(UpdateUserDetails userDetails, Integer userid) {
        Optional<User> users = userRepo.findById(userid);
        if (users.isEmpty()) {
            return new ResponseEntity<>("User Not Found", HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(new UpdateUserDetails(users.get().getId(),users.get().getUserName(),
                users.get().getEmail(),
                users.get().getProfilepic(),
                users.get().getDOB(),users.get().getGender()),HttpStatus.OK);
    }
}
