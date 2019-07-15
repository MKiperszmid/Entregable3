package com.example.com.entregable.Model.POJO;

public class Account {
    private String email;
    private String password;
    public Account(String email, String password){
        this.email = email;
        this.password = password;
    }

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

    public Boolean validLogin(){
        return email != null && password != null && email.length() > 0 && password.length() > 0 && email.contains("@");
    }
}
