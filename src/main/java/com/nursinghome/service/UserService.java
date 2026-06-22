package com.nursinghome.service;

import com.nursinghome.entity.User;
import java.util.List;

public interface UserService {
    User login(String username, String password);
    User findByUsername(String username);
    List<User> findAll();
    boolean add(User user);
    boolean update(User user);
    boolean delete(Integer id);
    boolean resetPassword(Integer id);
}