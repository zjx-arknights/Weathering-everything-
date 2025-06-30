package com.shixun.controller;


import com.shixun.config.CurrentUser;
import com.shixun.pojo.LoginInfo;
import com.shixun.pojo.Result;
import com.shixun.pojo.User;
import com.shixun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginResignController {
    @Autowired
    private UserService  userservice;
    @Autowired
    private CurrentUser currentUser;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        LoginInfo  loginInfo = userservice.login(user);
        //（用户名在数据库）密码输入错误
        if (loginInfo.getIsLogin()==-1) {
            return Result.success(loginInfo);
        }

        //登陆成功
        else if (loginInfo.getIsLogin()==1){
            currentUser.setCurrentUser(user.getUserName());
            return Result.success(loginInfo);
        }

        else {
            return Result.success(loginInfo);
        }

    }
}
