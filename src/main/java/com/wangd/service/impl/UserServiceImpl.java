package com.wangd.service.impl;


import com.wangd.dao.MenusDAO;
import com.wangd.dao.UserDAO;
import com.wangd.pojo.Menus;
import com.wangd.pojo.User;
import com.wangd.service.UserService;
import com.wangd.utils.MenusJson;
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

    public List<String> getMenus(){
        List<Menus> menus = menusDAO.getMenus();
        for (Menus menu : menus) {
            Menus menus1 = this.setChildren(menu);
//            Map<String, Object> menusJson = MenusJson.getMenusJson();
        }
        return null;
    }

    private Menus setChildren(Menus menus){
        List<Menus> childrenMenus = menusDAO.getChildrenMenus(menus.getMenuId());
        if (childrenMenus.isEmpty()){
            return menus;
        }
        List<Menus> children = menus.getChildren();
        for (Menus childrenMenu : childrenMenus) {
            Menus children1 = setChildren(childrenMenu);
            children.add(children1);
        }
        menus.setChildren(children);
        return menus;

    }
}
