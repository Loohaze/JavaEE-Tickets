package com.edu.nju.tickets.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 会员
 * userId
 * name             用户名
 * gender           性别
 * birthday         生日
 * mail             邮箱
 * level            会员级别
 * phone            手机号
 * points           积分
 */
@Entity
@Table(name = "user")
public class User implements Serializable{

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_gender")
    private String gender;

    @Column(name = "user_birth")
    private String birthday;

    @Column(name = "user_mail")
    private String mail;

    @Column(name = "user_level")
    private int level;

    @Column(name = "user_phone")
    private String phone;

    @Column(name = "user_points")
    private int points;

    @Column(name = "user_password")
    private String password;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday='" + birthday + '\'' +
                ", mail='" + mail + '\'' +
                ", level=" + level +
                ", phone='" + phone + '\'' +
                ", points=" + points +
                ", password=" + password + '\'' +
                '}';
    }
}
