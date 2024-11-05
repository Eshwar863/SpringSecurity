package com.SpringDemo.SpringSecurity.Repo;

import com.SpringDemo.SpringSecurity.Entity.Otp;
import com.SpringDemo.SpringSecurity.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OtpRepo extends JpaRepository<Otp, Integer> {
    Otp findByOtp(String otp);
    Otp findByUser(User user);
    @Query("select b from Otp b where b.user.userName =:username and b.otp=:otp")
    Otp findByOtpAndUser(String otp, String username);
}
