package com.SpringDemo.SpringSecurity.Dto;


public class UsersDto {

    private Integer id;
    private String userName;
    private String password;

    public UsersDto(Integer id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

}
