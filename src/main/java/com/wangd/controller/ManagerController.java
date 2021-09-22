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
@CrossOrigin(
        methods = {RequestMethod.PUT, RequestMethod.GET,
        RequestMethod.POST, RequestMethod.OPTIONS, RequestMethod.DELETE})
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
        System.out.println("queryParam = " + queryParam);
        System.out.println("currPage = " + currPage);
        System.out.println("pageSize = " + pageSize);
        List<Manager> managerList = managerService.queryAllManager(queryParam, currPage, pageSize);
        JSONObject managerResult = new JSONObject();
        managerResult.put("total", managerService.getCount());
        managerResult.put("pagenum", currPage);
        List<Object> jsonArray = new ArrayList<>();

        for (Manager manager : managerList) {
            // 接口返回模版 jsonObject
            Map<String, Object> jsonObject = new HashMap<>();
            jsonObject.put("id", manager.getId());
            jsonObject.put("username", manager.getUsername());
            jsonObject.put("mobile", manager.getMobile());
            jsonObject.put("type", manager.getType());
            jsonObject.put("email", manager.getEmail());
            jsonObject.put("create_time", manager.getRegistrationTime());
            jsonObject.put("mg_state", manager.getState() == 1);
            jsonObject.put("role_name", manager.getRole().getRoleName());
            jsonArray.add(jsonObject);
//            jsonObject.clear();
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
    @GetMapping("/users/{managerId}")
    public String editManager(@PathVariable("managerId") Integer managerId) {
        Manager manager = managerService.queryById(managerId);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", manager.getId());
        resultMap.put("username", manager.getUsername());
        resultMap.put("role_id", manager.getRoleId());
        resultMap.put("mobile", manager.getMobile());
        resultMap.put("email", manager.getEmail());
        requestResult.data = resultMap;
        requestResult.msg = "查询成功";
        return requestResult.getResult();
    }

    @ResponseBody
    @PutMapping("/users/{userid}/state/{type}")
    public String setupUserState(@PathVariable("userid") Integer userid,@PathVariable("type") Boolean type){
        System.out.println("type = " + type);
        // 判断返回值 1为开启 0为关闭
        Integer state = type ? 1:0;
        Manager manager = managerService.editState(userid, state);
        if (manager == null){
            requestResult.msg = "状态更改失败";
            requestResult.status = 304;
            return requestResult.getResult();
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", manager.getId());
        resultMap.put("username", manager.getUsername());
        resultMap.put("role_id", manager.getRoleId());
        resultMap.put("mobile", manager.getMobile());
        resultMap.put("email", manager.getEmail());
        requestResult.data = resultMap;
        requestResult.msg = "更改用户状态成功";
        return requestResult.getResult();
    }

    @ResponseBody
    @PutMapping("/users/{userid}")
    public String editManager(@RequestBody Manager manager){
        Manager editManager = managerService.editManager(manager);
        if (manager == null){
            requestResult.msg = "更新账户信息失败";
            requestResult.data = null;
            return requestResult.getResult();
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", editManager.getId());
        resultMap.put("username", editManager.getUsername());
        resultMap.put("role_id", editManager.getRoleId());
        resultMap.put("mobile", editManager.getMobile());
        resultMap.put("email", editManager.getEmail());
        requestResult.data = resultMap;
        requestResult.msg = "更改用户信息成功";
        return requestResult.getResult();
    }

    @ResponseBody
    @DeleteMapping("/users/{userid}")
    public String deleteManager(@PathVariable("userid") Integer userid){
        int deleteCount = managerService.deleteById(userid);
        requestResult.data = null;
        if (deleteCount == 0){
            requestResult.msg = "删除失败";
            requestResult.status = 202;
            return requestResult.getResult();
        }
        requestResult.msg = "删除成功";
        return requestResult.getResult();
    }

}
