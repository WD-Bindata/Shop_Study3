package com.wangd.service;

import com.wangd.pojo.Menus;
import com.wangd.pojo.User;

import java.util.List;
import java.util.Map;

/**
 * @author wangd
 */
public interface UserService {
    public User login(String username, String password);

    public Map<Integer, Menus> getMenus();

    public Map<Integer, Menus> getRoleHelp();

}
