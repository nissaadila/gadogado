package com.example.gadogado.model;

public class Search {
    private String usernameUser;
    private String ProfilePicPath;
    private String accountStatus;

    public Search(String usernameUser, String profilePicPath, String accountStatus) {
        this.usernameUser = usernameUser;
        ProfilePicPath = profilePicPath;
        this.accountStatus = accountStatus;
    }

    public String getUsernameUser() {
        return usernameUser;
    }

    public void setUsernameUser(String usernameUser) {
        this.usernameUser = usernameUser;
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
