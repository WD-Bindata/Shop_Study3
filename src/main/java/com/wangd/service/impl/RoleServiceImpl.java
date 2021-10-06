package com.wangd.service.impl;

import com.wangd.dao.MenusDAO;
import com.wangd.dao.RoleDAO;
import com.wangd.pojo.Menus;
import com.wangd.pojo.Role;
import com.wangd.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author wangd
 */
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private MenusDAO menusDAO;

    @Autowired
    private RoleDAO roleDAO;


    @Override
    public List<Role> getRoles() {
        return roleDAO.queryAllRole();
    }

    @Override
    public Map<Integer, Menus> getPermissions() {

        // 根据 , 对字符串进行切割 然后将其转换为List
//        List<String> roleIds = Arrays.asList(role.getRoleIds().split(","));

        List<Menus> menusList = menusDAO.queryAllPermission();
        menusList.sort((o1, o2) -> {
            if (o1.getLevel() < o2.getLevel()){
                return -1;
            }
            return 0;

        });
        // 存放权限1
        Map<Integer, Menus> result = new HashMap<>();
        // 存放权限2
        Map<Integer, Menus> jurisdiction2 = new HashMap<>();
        // 存放权限3
        Map<Integer, Menus> jurisdiction3 = new HashMap<>();
        for (Menus menu : menusList) {
            if (menu.getLevel() == 0){
                System.out.println("menu.getMenuId() = " + menu.getMenuId());
                result.put(menu.getMenuId(), menu);
            } else if (menu.getLevel() == 1){
                Menus menus1 = result.get(menu.getFatherMenuId());
                if (menus1==null){
                    continue;
                }
                List<Menus> children = menus1.getChildren();
                children.add(menu);
                jurisdiction2.put(menu.getMenuId(), menu);
            }
        }
        // 判断三级标签是否存在
        for (Menus menus3 : menusList) {
            if (menus3.getLevel() != 2
                    && !jurisdiction2.containsKey(menus3.getFatherMenuId())
                    || jurisdiction2.get(menus3.getFatherMenuId()) == null){
                continue;
            }

            Menus menus2 = jurisdiction2.get(menus3.getFatherMenuId());
            Object fatherMenuId = result.get(menus2.getFatherMenuId()).getFatherMenuId();
            menus3.setFatherMenuId(fatherMenuId + "," + menus2.getFatherMenuId());
            menus2.getChildren().add(menus3);


            jurisdiction3.put(menus3.getMenuId(), menus3);
        }


        return result;
    }

    @Transactional
    @Override
    public Map<Integer, Menus> deletePermissions(String[] ids, Integer delRoleId) {
        Map<Integer, Menus> menusMap = this.getPermissions();
        if (ids == null){
            return menusMap;
        }


        List<String> idList = Arrays.asList(ids);
//        Map<Integer, Menus> result = new HashMap<>();
        for (Integer pms : menusMap.keySet()) {
            // 获取一级权限
            Menus menus1 = menusMap.get(pms);
//            int popFlag = 0;
            if (menus1.getChildren().isEmpty() || menus1.getMenuId().equals(delRoleId)){
                continue;
            }
            // 获得二级权限列表
            List<Menus> menusList2 = menus1.getChildren();
            // 这里使用长度的方式实现循环 是为了避免出现 ConcurrentModificationException 并发修改异常
            for (int i = 0; i < menusList2.size(); i++) {
                Menus menus2 = menusList2.get(i);
                if (menus2.getChildren().isEmpty() || menus2.getMenuId().equals(delRoleId)){
                    continue;
                }
                // 获得三级权限
                List<Menus> menusList3 = menus2.getChildren();
                for (int j = 0; j < menusList3.size(); j++) {
                    Menus menus3 = menusList3.get(j);
                    // 判断三级权限是否在ids里面 不在则将其删除
                    if (!idList.contains(String.valueOf(menus3.getMenuId())) || menus3.getMenuId().equals(delRoleId)){
                        menusList3.remove(menus3);
                        j--;
                    }
                }
                // 权限2下的权限3都不在ids里面 则将当前的权限2删除
                if (menusList3.isEmpty()){
                    menusList2.remove(menus2);
                    i--;
                }
            }
            // 权限1下的权限2都不再ids里面 则将当前权限1删除
            if (menusList2.isEmpty()){
                menusMap.remove(pms, menus1);
            }
        }
        return menusMap;
    }


    @Override
    public Map<Integer, Menus> getScreenPermissions(List<String> idList) {
        // 使用ConcurrentHashMap 解决并发修改问题 ConcurrentModificationException
        ConcurrentHashMap<Integer, Menus> menusMap = new ConcurrentHashMap<>(getPermissions());
        if (idList == null || idList.isEmpty()){
            return menusMap;
        }

        Iterator<Integer> iterator = menusMap.keySet().iterator();



        while (iterator.hasNext()) {
            // 获取一级权限
            Integer key = iterator.next();
            Menus menus1 = menusMap.get(key);

//            int popFlag = 0;
            if (menus1.getChildren().isEmpty()){
                continue;
            }
            // 获得二级权限列表
            List<Menus> menusList2 = menus1.getChildren();
            // 这里使用长度的方式实现循环 是为了避免出现 ConcurrentModificationException 并发修改异常
            for (int i = 0; i < menusList2.size(); i++) {
                Menus menus2 = menusList2.get(i);
                if (menus2.getChildren().isEmpty()){
                    continue;
                }
                // 获得三级权限
                List<Menus> menusList3 = menus2.getChildren();
                for (int j = 0; j < menusList3.size(); j++) {
                    Menus menus3 = menusList3.get(j);
                    // 判断三级权限是否在ids里面 不在则将其删除
                    if (!idList.contains(String.valueOf(menus3.getMenuId()))){
                        menusList3.remove(menus3);
                        j--;
                    }
                }
                // 权限2下的权限3都不在ids里面 则将当前的权限2删除
                if (menusList3.isEmpty()){
                    menusList2.remove(menus2);
                    i--;
                }
            }
            // 权限1下的权限2都不再ids里面 则将当前权限1删除
            if (menusList2.isEmpty()){
                menusMap.remove(key, menus1);
            }
        }
        return menusMap;
    }



    @Transactional
    @Override
    public int addRole(Role role) {

        return roleDAO.addRole(role);
    }


    @Override
    public Role getOneRole(Integer roleId) {
        return roleDAO.queryOneRole(roleId);
    }

    @Transactional
    @Override
    public int editRole(Role role) {
        return roleDAO.updateRole(role);
    }

    @Transactional
    @Override
    public int deleteRole(Integer roleId) {
        return roleDAO.deleteByRoleId(roleId);
    }

    @Transactional
    @Override
    public List<Menus> getPermissionsList() {
        List<Menus> menusList = menusDAO.queryAllPermission();
        menusList.sort((o1, o2) -> {
            if (o1.getLevel() < o2.getLevel()){
                return -1;
            }
            return 0;

        });
        return menusList;
    }

    @Transactional
    @Override
    public int updatePermission(Integer roleId, String roleIds) {
        return roleDAO.updateRoleIds(roleId, roleIds);
    }
}
