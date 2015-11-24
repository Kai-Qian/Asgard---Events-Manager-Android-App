package com.brynhildr.asgard.entities;

/**
 * Created by willQian on 2015/11/13.
 */
public class User {
    /**
     * Student userName
     */
    private String userName;
    /**
     * Store email
     */
    private String email;
    private String phoneNum;
    private String passWord;
    private String gender;

    public User(String userName, String email, String phoneNum, String passWord, String gender) {
        this.userName = userName;
        this.email = email;
        this.phoneNum = phoneNum;
        this.passWord = passWord;
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
