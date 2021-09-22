package com.wangd.service.impl;

import com.wangd.dao.MenusDAO;
import com.wangd.dao.RoleDAO;
import com.wangd.pojo.Menus;
import com.wangd.pojo.Role;
import com.wangd.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangd
 */
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
}
