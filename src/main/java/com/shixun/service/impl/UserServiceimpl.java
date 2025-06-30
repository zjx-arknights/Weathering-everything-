package com.shixun.service.impl;

import com.shixun.mapper.UserMapper;
import com.shixun.pojo.LoginInfo;
import com.shixun.pojo.User;
import com.shixun.service.UserService;
import com.shixun.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceimpl implements UserService {
    @Autowired
    private UserMapper usermapper;
    @Override
    public LoginInfo login(User user) {
        User user1 = usermapper.selectUser(user);
        if(user1!=null) {
            Map<String,Object> info = new HashMap<>();
            info.put("id",user1.getId());
            String token = JwtUtils.generateToken( info);
            return new LoginInfo(user1.getUserName(), user1.getPassword(),user1.getLocationid() , 1);
        }

        else {
            User user2 = usermapper.selectUserOnlyByName(user);
            if (user2 != null){
                return new LoginInfo(user2.getUserName(), user2.getPassword(), "000", -1);
            }
            else {
                usermapper.insertUser(user);
                return new LoginInfo(user.getUserName(), user.getPassword(), "000", 0);}
        }
    }
}
