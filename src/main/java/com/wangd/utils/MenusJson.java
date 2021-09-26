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

    public static Map<String, Object> getTreeJson(Menus menus){
        Map<String, Object> menusMap = new HashMap<>();
        menusMap.put("id", menus.getMenuId());
        menusMap.put("authName", menus.getMenuName());
        menusMap.put("path", menus.getApiPath());
        // 二级权限列表
        List<Menus> childrenList2 = menus.getChildren();
        List<Map<String, Object>> rightsList2 = new ArrayList<>();
        for (Menus menusChild : childrenList2) {
            if (menusChild.getChildren().isEmpty()){
                continue;
            }
            Map<String, Object> map = rightsJSONMapping(menusChild);
            // 三级权限列表
            List<Menus> childrenList3 = menusChild.getChildren();
            List<Map<String, Object>> rightsList3 = new ArrayList<>();
            for (Menus rights3 : childrenList3) {
                Map<String, Object> map1 = rightsJSONMapping(rights3);
                rightsList3.add(map1);
            }
            rightsList2.add(map);
        }
        menusMap.put("children", rightsList2);
        return menusMap;

    }

    public static Map<String, Object> rightsJSONMapping(Menus menus){
        Map<String, Object> menusMap = new HashMap<>();
        menusMap.put("id", menus.getMenuId());
        menusMap.put("authName", menus.getMenuName());
        menusMap.put("path", menus.getApiPath());
        menusMap.put("pid", menus.getFatherMenuId());
        menusMap.put("level", String.valueOf(menus.getLevel()));
        return menusMap;
    }

    public static JSONArray permissionsErgodicJSON(Map<Integer, Menus> Permissions){
        JSONArray jsonArray = new JSONArray();
        for (Integer integer : Permissions.keySet()) {
            Menus menus1 = Permissions.get(integer);
            // 将一级权限映射
            Map<String, Object> menusJson = MenusJson.rightsJSONMapping(menus1);
            // 获取二级权限
            List<Menus> menusList2 = menus1.getChildren();
            // 存放二级权限
            ArrayList<Map> menus1Children = new ArrayList<>();

            menusList2.forEach(menus2 -> {
                // 并将二级权限进行映射
                Map<String, Object> menusJson2 = MenusJson.rightsJSONMapping(menus2);
                // 存放三级权限
                ArrayList<Map> menus2Children = new ArrayList<>();
                menus2.getChildren().forEach(menus3 -> {
                    Map<String, Object> menus3Map = MenusJson.rightsJSONMapping(menus3);
                    menus3Map.put("children", null);
                    menus2Children.add(menus3Map);
                });

                menusJson2.put("children", menus2Children);

                menus1Children.add(menusJson2);

            });
            menusJson.put("children", menus1Children);
            jsonArray.add(menusJson);

        }
        return jsonArray;
    }


}
