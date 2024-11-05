package com.SpringDemo.SpringSecurity.Service;

import com.SpringDemo.SpringSecurity.Entity.User;
import com.SpringDemo.SpringSecurity.Entity.UserPrinciple;
import com.SpringDemo.SpringSecurity.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
   final
   UserRepo userRepo;

    public MyUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUserName(username);
        if (user==null)
        {
            throw new UsernameNotFoundException("User not found");
        }        return new UserPrinciple(user);
    }
}
