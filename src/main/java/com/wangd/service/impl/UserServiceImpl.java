package com.wangd.service.impl;


import com.wangd.dao.MenusDAO;
import com.wangd.dao.UserDAO;
import com.wangd.pojo.Menus;
import com.wangd.pojo.User;
import com.wangd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author wangd
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    private MenusDAO menusDAO;
    @Override
    public int addUser(User user) {
        return userDAO.insert(user);
    }

    @Override
    public User login(String username, String password) {
        User user = userDAO.selectByUsername(username, password);

        return user;
    }

    public Map<String, Object> getMenus(){
        List<Menus> menus = menusDAO.getMenus();
        return null;
    }
}
