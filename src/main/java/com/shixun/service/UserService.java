package com.shixun.service;

import com.shixun.pojo.LoginInfo;
import com.shixun.pojo.User;



public interface UserService {
    LoginInfo login(User user);
}
