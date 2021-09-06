package com.wangd.service;

import com.wangd.pojo.User;

/**
 * @author wangd
 */
public interface UserService {
    public int addUser(User user);

    public User login(String username, String password);
}
