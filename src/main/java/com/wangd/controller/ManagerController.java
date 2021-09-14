package com.wangd.controller;

import com.alibaba.fastjson.*;
import com.alibaba.fastjson.support.spring.annotation.ResponseJSONP;
import com.sun.tools.javac.util.StringUtils;
import com.wangd.pojo.Manager;
import com.wangd.pojo.User;
import com.wangd.service.ManagerService;
import com.wangd.utils.RequestResult;
import com.wangd.utils.TokenUtils;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.io.StringWriter;
import java.io.Writer;
import java.sql.Time;
import java.util.*;

/**
 * @author wangd
 */
@Controller
@CrossOrigin(methods = {RequestMethod.PUT, RequestMethod.GET, RequestMethod.POST})
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    private RequestResult requestResult = new RequestResult();

    @ResponseBody
    @PostMapping("/login")
    public String login(@RequestBody Manager managerParam){
        System.out.println("requestJson = " + managerParam);

//        JSONObject jsonObject1 = JSONObject.parseObject(managerParam);
        String username = managerParam.getUsername();
        String password = managerParam.getPassword();



        System.out.println("username = " + username);

        System.out.println("password = " + password);
        Manager manager = managerService.login(username, password);
        if (manager == null){
            requestResult.msg = "查询账户失败 请确认账户或者密码" + manager;
            requestResult.status = 501;
            return requestResult.getResult();
        }
        Map<String, Object> managerMap = new HashMap<>();

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


    @ResponseBody
    @GetMapping("/users")
    public String getUsers(@RequestParam("query") String queryParam, @RequestParam("pagenum") Integer currPage, @RequestParam("pagesize") Integer pageSize){
        List<Manager> managerList = managerService.queryAllManager(queryParam, currPage, pageSize);
        JSONObject managerResult = new JSONObject();
        managerResult.put("total", managerList.size());
        managerResult.put("pagenum", currPage);
        List<Object> jsonArray = new ArrayList<>();
        Map<String, Object> jsonObject = new HashMap<>();
        for (Manager manager : managerList) {

            jsonObject.put("id", manager.getId());
            jsonObject.put("username", manager.getUsername());
            jsonObject.put("mobile", manager.getMobile());
            jsonObject.put("type", manager.getType());
            jsonObject.put("email", manager.getEmail());
            jsonObject.put("create_time", manager.getRegistrationTime());
            jsonObject.put("mg_state", manager.getState() == 1);
            jsonObject.put("role_name", manager.getRole().getRoleName());
            jsonArray.add(jsonObject);
        }
        managerResult.put("users", jsonArray);
        requestResult.data = managerResult;
        return requestResult.getResult();
    }


    @ResponseBody
    @PostMapping("/users")
    public String addManager(@RequestBody Manager manager){
        System.out.println("manager = " + manager);
        manager.setRegistrationTime(Math.toIntExact(System.currentTimeMillis()/ 1000));
        manager.setState(1);
        int i = managerService.addManager(manager);
        if (i == 1){
            Map<String, Object> managerMap = new HashMap<>();
            managerMap.put("id", manager.getId());
            managerMap.put("mobile", manager.getMobile());
            managerMap.put("email", manager.getEmail());
            managerMap.put("create_time", manager.getRegistrationTime());
            managerMap.put("username", manager.getUsername());
            requestResult.data = managerMap;

            return requestResult.getResult();
        }
        requestResult.msg = "添加失败";
        requestResult.status = 304;
        return requestResult.getResult();
    }

    @ResponseBody
    @PutMapping("/users/{managerId}")
    public String editManager(@PathVariable("managerId") Integer managerId) {
        System.out.println("managerId = " + managerId);
        return null;
    }
}
