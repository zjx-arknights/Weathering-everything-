package com.shixun.config;


import org.springframework.stereotype.Component;

//此类用于记录当前登录的用户
@Component
public class CurrentUser {
    private String currentUser = "null";

    public String getCurrentUser() {
        return currentUser;
    }
    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

}