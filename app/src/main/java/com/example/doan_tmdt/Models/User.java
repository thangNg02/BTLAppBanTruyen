package com.example.doan_tmdt.Models;

public class User {
    private String iduser;
    private String email;
    private String name;
    private String address;
    private String phone;
    private String date;
    private String sex;
    private String password;

    public User() {
    }

    public User(String iduser, String email, String name, String address, String phone, String date, String sex) {
        this.iduser = iduser;
        this.email = email;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.date = date;
        this.sex = sex;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
