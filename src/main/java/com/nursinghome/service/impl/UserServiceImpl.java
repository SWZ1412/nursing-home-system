package com.nursinghome.service.impl;

import com.nursinghome.entity.User;
import com.nursinghome.mapper.UserMapper;
import com.nursinghome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        return userMapper.login(username, md5Password);
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public boolean add(User user) {
        user.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes())); // 默认密码
        user.setStatus(1);
        return userMapper.insert(user) > 0;
    }

    @Override
    public boolean update(User user) {
        // 如果前端没有传status或status无效，保留原来的状态
        if (user.getStatus() == null) {
            User existing = userMapper.findById(user.getId());
            if (existing != null) {
                user.setStatus(existing.getStatus());
            } else {
                user.setStatus(1); // 默认启用
            }
        }
        return userMapper.update(user) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return userMapper.deleteById(id) > 0;
    }

    @Override
    public boolean resetPassword(Integer id) {
        String newPwd = DigestUtils.md5DigestAsHex("123456".getBytes());
        return userMapper.resetPassword(id, newPwd) > 0;
    }
}