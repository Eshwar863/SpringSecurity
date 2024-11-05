package com.SpringDemo.SpringSecurity.Dto;

public class AllUsers {

    private Integer id;
    private String userName;
    private String email;
    private String profilepic;
    private String DOB;
    private String gender;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public AllUsers(Integer id, String userName, String email, String profilepic, String DOB, String gender) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.profilepic = profilepic;
        this.DOB = DOB;
        this.gender = gender;
    }
}
