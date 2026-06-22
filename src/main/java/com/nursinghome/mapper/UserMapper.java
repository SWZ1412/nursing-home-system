package com.nursinghome.mapper;

import com.nursinghome.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(@Param("id") Integer id);

    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(@Param("username") String username);

    @Select("SELECT * FROM user WHERE username = #{username} AND password = #{password}")
    User login(@Param("username") String username, @Param("password") String password);

    @Select("SELECT * FROM user ORDER BY id DESC")
    List<User> findAll();

    @Insert("INSERT INTO user (username, password, real_name, role, phone, email, status) " +
            "VALUES (#{username}, #{password}, #{realName}, #{role}, #{phone}, #{email}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Update("UPDATE user SET real_name=#{realName}, role=#{role}, phone=#{phone}, email=#{email}, status=#{status} WHERE id=#{id}")
    int update(User user);

    @Delete("DELETE FROM user WHERE id = #{id}")
    int deleteById(@Param("id") Integer id);

    @Update("UPDATE user SET password = #{password} WHERE id = #{id}")
    int resetPassword(@Param("id") Integer id, @Param("password") String password);
}