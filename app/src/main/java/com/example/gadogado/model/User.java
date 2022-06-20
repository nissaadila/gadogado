package com.example.gadogado.model;

public class User{
    private String usernameUser;
    private  String emailUser;
    private String passwordUser;
    private String ProfilePicPath;
    private String accountStatus;

    public User(String usernameUser, String emailUser, String passwordUser, String profilePicPath, String accountStatus) {
        this.usernameUser = usernameUser;
        this.emailUser = emailUser;
        this.passwordUser = passwordUser;
        ProfilePicPath = profilePicPath;
        this.accountStatus = accountStatus;
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

    public String getProfilePicPath() {
        return ProfilePicPath;
    }

    public void setProfilePicPath(String profilePicPath) {
        ProfilePicPath = profilePicPath;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }
}
