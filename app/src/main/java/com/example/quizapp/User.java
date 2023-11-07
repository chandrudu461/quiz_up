package com.example.quizapp;

public class User {
    public String email,password,role;

    public User(String email, String password, String role,String name) {
        this.email = email;
        this.password = password;
        this.role = role;
//        this.name = name;
    }

    public User(){}
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

//    public void setName(String name){
//        this.name = name;
//    }

    public void setRole(String role) {
        this.role = role;
    }
}
