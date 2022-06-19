package com.example.gadogado.model;

public class User{
    private String usernameUser;
    private  String emailUser;
    private String passwordUser;

    public User(String usernameUser, String emailUser, String passwordUser) {
        this.usernameUser = usernameUser;
        this.emailUser = emailUser;
        this.passwordUser = passwordUser;
    }

    public String getUsernameUser() {
        return usernameUser;
    }

    public void setUsernameUser(String usernameUser) {
        this.usernameUser = usernameUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }
}
