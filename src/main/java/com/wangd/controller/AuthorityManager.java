package com.wangd.controller;

import com.alibaba.fastjson.JSONArray;
import com.wangd.pojo.Menus;
import com.wangd.pojo.Role;
import com.wangd.service.RoleService;
import com.wangd.service.UserService;
import com.wangd.utils.MenusJson;
import com.wangd.utils.RequestResult;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @author wangd
 * 权限管理控制器
 */
@Controller
@CrossOrigin
public class AuthorityManager {
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    public RequestResult requestResult = new RequestResult();

    @ResponseBody
    @GetMapping("/roles")
    public String getRoles(){
        List<Role> roles = roleService.getRoles();
        Map<Integer, Menus> menus = userService.getMenus();
        menus.remove(null);

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < roles.size(); i++) {
            HashMap<String, Object> roleMap = new HashMap<>();
            Role role = roles.get(i);
            String roleIds = role.getRoleIds();

            roleMap.put("id", role.getRoleId());
            roleMap.put("roleName", role.getRoleName());
            roleMap.put("roleDesc", role.getRoleDesc());
            List<Object> children = new ArrayList<>();
            List<String> stringList = Arrays.asList(roleIds.split(","));
            stringList.forEach(id -> {
                if ("".equals(id) || Integer.parseInt(id) == 0){
                }else {
                    Menus menus1 = menus.get(Integer.parseInt(id));
                    if (menus1 == null) {
                        return;
                    }

                    Map<String, Object> menusJson = MenusJson.getMenusJson(menus1);
                    children.add(menusJson);
                }
            });
            roleMap.put("children", children);

            jsonArray.add(roleMap);
        }
        requestResult.data = jsonArray;
        return requestResult.getResult();
    }
}
