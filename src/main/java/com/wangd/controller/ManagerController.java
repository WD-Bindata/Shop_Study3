package com.wangd.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONStreamAware;
import com.alibaba.fastjson.JSONWriter;
import com.sun.tools.javac.util.StringUtils;
import com.wangd.pojo.Manager;
import com.wangd.service.ManagerService;
import com.wangd.utils.TokenUtils;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangd
 */
@Controller
@CrossOrigin
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @ResponseBody
    @PostMapping("/login")

    public String login(@RequestBody String requestJson){

        JSONObject jsonObject1 = JSONObject.parseObject(requestJson).getJSONObject("data");
        String username = jsonObject1.getString("username");
        String password = jsonObject1.getString("password");



        System.out.println("username = " + username);

        System.out.println("password = " + password);
        Manager manager = managerService.login(username, password);

        Map managerMap = new HashMap();
        managerMap.put("id", manager.getId().toString());
        managerMap.put("rid", manager.getRoleId());
        managerMap.put("username", manager.getUsername());
        managerMap.put("mobile", manager.getMobile());
        managerMap.put("email", manager.getEmail());
        managerMap.put("token", TokenUtils.sign(username, password));



        Map<String, Object> metaMap = new HashMap<>();
        metaMap.put("msg", "登录成功");
        metaMap.put("status", 200);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", managerMap);
        jsonObject.put("meta", metaMap);
        return jsonObject.toJSONString();

    }
}
