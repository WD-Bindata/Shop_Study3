package com.wangd.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangd.pojo.Menus;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangd
 */
public class MenusJson {

    public static Map<String, Object> getMenusJson(Menus menus){
        Map<String, Object> menusMap = new HashMap<>();
        menusMap.put("id", menus.getMenuId());
        menusMap.put("authName", menus.getMenuName());
        menusMap.put("path", menus.getApiPath());
        List<Menus> children = menus.getChildren();
        List<Map<String, Object>> childrenList = new ArrayList<>();
        if (!children.isEmpty()){
            children.forEach(childrenMenu -> {
                Map<String, Object> menusJson = MenusJson.getMenusJson(childrenMenu);
                childrenList.add(menusJson);

            });
        }
        menusMap.put("children", childrenList);
        return menusMap;
    }


}
