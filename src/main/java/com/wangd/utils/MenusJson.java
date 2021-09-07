package com.wangd.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangd.pojo.Menus;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangd
 */
public class MenusJson {

    public static Map<String, Object> getMenusJson(List<Menus> menus){
        Map<String, Object> menusMap = new HashMap<>();
        for (Menus menu : menus) {

            menusMap.put("id", menu.getMenuId());
            menusMap.put("authName", menu.getMenuName());
            menusMap.put("path", menu.getApiPath());

        }

        return menusMap;
    }


}
