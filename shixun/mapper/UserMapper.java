package com.shixun.mapper;

import com.shixun.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    @Select("select * from users where username=#{userName} and password=#{password}")
    User selectUser(User user);

    @Insert("insert into users(username,password) values(#{userName},#{password})")
    void insertUser(User user);

    @Select("select * from users where username=#{userName}")
    User selectUserOnlyByName(User user);

    @Update("UPDATE users SET locationid = #{locationid} WHERE username = #{currentUser}")
    void updateuserlocationid(String currentUser, String locationid);
}
