package com.shixun.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginInfo
{
    private String username;
    private String password;
    private String token;
    private int isLogin; // 0表示未登录，1表示已登录
}
