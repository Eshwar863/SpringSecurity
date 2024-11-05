package com.SpringDemo.SpringSecurity.Entity;

import jakarta.persistence.*;

import lombok.Data;
@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String password;
    private String email;
    private String profilepic;
    private String DOB;
    private String gender;
}
