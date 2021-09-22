package com.wangd.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangd.pojo.Menus;
import com.wangd.pojo.User;
import com.wangd.service.UserService;
import com.wangd.utils.MenusJson;
import com.wangd.utils.RequestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author wangd
 */
@Controller
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    private RequestResult result = new RequestResult();

    @ResponseBody
    @RequestMapping(value = "/menus", method = {RequestMethod.GET, RequestMethod.OPTIONS})
    public String getMenus(){
        Map<Integer, Menus> menus = userService.getMenus();
        List<Map> menusList = new ArrayList<>();
        for (Integer integer : menus.keySet()) {
            Menus menus1 = menus.get(integer);
            Map<String, Object> menusJson = MenusJson.getMenusJson(menus1);
            System.out.println("menus1 = " + menus1.getApiOrder());
            menusList.add(menusJson);

        }
        // 列表反转
        Collections.reverse(menusList);
        result.data = menusList;
        result.msg = "获取菜单列表成功";
        String result = this.result.getResult();
        return result;
    }
}
