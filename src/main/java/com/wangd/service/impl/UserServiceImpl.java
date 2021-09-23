package com.wangd.service.impl;


import com.wangd.dao.MenusDAO;
import com.wangd.dao.UserDAO;
import com.wangd.pojo.Menus;
import com.wangd.pojo.User;
import com.wangd.service.UserService;
import com.wangd.utils.MenusJson;
import org.apache.logging.log4j.util.Strings;
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


        return userDAO.selectByUsername(username, password);
    }

    @Override
    public Map<Integer, Menus> getMenus(){
        List<Menus> menusList = menusDAO.queryAllMenus();
        // 根据标签排序
        menusList.sort((o1, o2) -> {
            if (o1.getLevel() < o2.getLevel()){
                return -1;
            } else {
                return 0;
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

    @Override
    public Map<Integer, Menus> getRoleHelp() {

        List<Menus> menusList = menusDAO.queryAllMenus();

        List<Menus> roleHelpList = new ArrayList<>();
//        List<String> stringList = Arrays.asList(ids);
//        menusList.forEach(menus -> {
//            Integer menuId = menus.getMenuId();
//            if (stringList.contains(String.valueOf(menuId))){
//             roleHelpList.add(menus);
//            }
//        });
        // 根据标签排序
        menusList.sort((o1, o2) -> {
            if (o1.getLevel() < o2.getLevel()){
                return -1;
            } else {
                return 0;
            }

        });


        Map<Integer, Menus> oneMenus = new HashMap<>();

        for (Menus menu : menusList) {
            if (menu.getLevel() == 0){
                oneMenus.put(menu.getMenuId(), menu);
            } else if (menu.getLevel() == 1){
                Menus menus1 = oneMenus.get(menu.getFatherMenuId());

                List<Menus> children = menus1.getChildren();
                children.add(menu);
            } else if (menu.getLevel() == 2){
                Menus menus2 = oneMenus.get(menu.getFatherMenuId());
                if (menus2 == null){
                    continue;
                }

                List<Menus> children = menus2.getChildren();
                menu.setChildren(null);
                children.add(menu);
            }
        }


        return oneMenus;
    }

    private Menus searchMenus(Map<Integer, Menus> menusMap, Menus menu){
        for (Integer integer : menusMap.keySet()) {
            if (integer.equals(menu.getMenuId())){
                return menusMap.get(integer);
            }
        }
        return null;
    }

}
