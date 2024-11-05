package com.SpringDemo.SpringSecurity.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
public class Otp {
    @Id
    private String otp;
    private LocalDateTime otpTime;
    @OneToOne
    private User user;
}
