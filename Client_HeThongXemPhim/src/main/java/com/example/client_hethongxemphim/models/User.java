package com.example.client_hethongxemphim.models;

import java.io.Serializable;
import java.sql.Date;

public class User implements Serializable {

    private String UserName;

    private String PassWord;

    private String Role;

    private String FullName;

    private Date Dob;

    private String PhoneNumber;

    private String Email;

    @Override
    public String toString() {
        return "User{" +
                "UserName='" + UserName + '\'' +
                ", PassWord='" + PassWord + '\'' +
                ", Role='" + Role + '\'' +
                ", FullName='" + FullName + '\'' +
                ", Dob=" + Dob +
                ", PhoneNumber='" + PhoneNumber + '\'' +
                ", Email='" + Email + '\'' +
                '}';
    }

    public User() {
    }

    public User(String userName, String passWord, String role, String fullName, Date dob, String phoneNumber, String email) {
        UserName = userName;
        PassWord = passWord;
        Role = role;
        FullName = fullName;
        Dob = dob;
        PhoneNumber = phoneNumber;
        Email = email;
    }
    public User( String passWord, String role, String fullName, Date dob, String phoneNumber, String email) {

        PassWord = passWord;
        Role = role;
        FullName = fullName;
        Dob = dob;
        PhoneNumber = phoneNumber;
        Email = email;
    }

    // Getters and Setters
    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        this.UserName = userName;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        this.PassWord = passWord;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        this.Role = role;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        this.FullName = fullName;
    }

    public Date getDob() {
        return Dob;
    }

    public void setDob(Date dob) {
        this.Dob = dob;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.PhoneNumber = phoneNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }
}

