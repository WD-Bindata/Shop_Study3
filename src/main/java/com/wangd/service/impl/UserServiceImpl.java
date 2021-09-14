package com.wangd.service.impl;


import com.wangd.dao.MenusDAO;
import com.wangd.dao.UserDAO;
import com.wangd.pojo.Menus;
import com.wangd.pojo.User;
import com.wangd.service.UserService;
import com.wangd.utils.MenusJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author wangd
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
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

    @Override
    public Map<Integer, Menus> getMenus(){
        List<Menus> menusList = menusDAO.getMenus();
        // 根据标签排序
        menusList.sort(new Comparator<Menus>() {
            @Override
            public int compare(Menus o1, Menus o2) {
                if (o1.getLevel() < o2.getLevel()){
                    return -1;
                } else {
                    return 0;
                }

            }
        });
        Map<Integer, Menus> oneMenus = new HashMap<>();
        for (Menus menu : menusList) {
            if (menu.getLevel() == 0){
                oneMenus.put(menu.getMenuId(), menu);
            } else if (menu.getLevel() == 1){
                Menus menus2 = oneMenus.get(menu.getFatherMenuId());
                List<Menus> children = menus2.getChildren();
                children.add(menu);
            }
        }
        return oneMenus;
    }

}
