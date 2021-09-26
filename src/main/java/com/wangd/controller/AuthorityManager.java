package com.wangd.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangd.pojo.Manager;
import com.wangd.pojo.Menus;
import com.wangd.pojo.Role;
import com.wangd.service.ManagerService;
import com.wangd.service.RoleService;
import com.wangd.service.UserService;
import com.wangd.utils.MenusJson;
import com.wangd.utils.RequestResult;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author wangd
 * 权限管理控制器
 */
@Controller
@CrossOrigin
@ResponseBody
public class AuthorityManager {
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    ManagerService managerService;

    public RequestResult requestResult = new RequestResult();


    // 获取所有角色和其对应的权限
    @GetMapping("/roles")
    public String getRoles(){
        List<Role> roles = roleService.getRoles();
        Map<Integer, Menus> menus = roleService.getPermissions();

        menus.remove(null);

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < roles.size(); i++) {
            HashMap<String, Object> roleMap = new HashMap<>();
            Role role = roles.get(i);
            String roleIds = role.getRoleIds();

            roleMap.put("id", role.getRoleId());
            roleMap.put("roleName", role.getRoleName());
            roleMap.put("roleDesc", role.getRoleDesc());
            List<String> stringList = Arrays.asList(roleIds.split(","));
            Map<Integer, Menus> screenPermissions = roleService.screenPermissions(stringList);
            roleMap.put("children", MenusJson.permissionsErgodicJSON(screenPermissions));

            jsonArray.add(roleMap);
        }
        requestResult.data = jsonArray;
        return requestResult.getResult();
    }


    @PutMapping("/users/{userId}/role")
    public String modifyManagerRole(@PathVariable("userId") Integer roleId, @RequestBody Map<String, Integer> modifyParam){
        Manager manager = managerService.queryById(modifyParam.get("id"));
        manager.setRoleId(modifyParam.get("rid"));
        managerService.editManager(manager);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", manager.getId());
        resultMap.put("username", manager.getUsername());
        resultMap.put("role_id", manager.getRoleId());
        resultMap.put("mobile", manager.getMobile());
        resultMap.put("email", manager.getEmail());
        requestResult.data = resultMap;
        requestResult.msg = "更改用户角色成功";
        return requestResult.getResult();
    }



    @GetMapping("/rights/tree")
    public String getRightsTree(){
        Map<Integer, Menus> permissions = roleService.getPermissions();
        requestResult.data = MenusJson.permissionsErgodicJSON(permissions);
        requestResult.msg = "获取菜单列表成功";

        return requestResult.getResult();
    }



    @GetMapping("/rights/list")
    public String getRightsList(){
        List<Menus> permissionsList = roleService.getPermissionsList();
        JSONArray jsonArray = new JSONArray();
        permissionsList.forEach(menus -> {
            jsonArray.add(MenusJson.rightsJSONMapping(menus));
        });
        requestResult.data = jsonArray;
        requestResult.msg = "获取权限列表成功";

        return requestResult.getResult();
    }


    @PostMapping("/roles")
    public String addRole(@RequestBody Role role){
        int i = roleService.addRole(role);
        if (i == 1){
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("roleId", role.getRoleId());
            resultMap.put("roleName", role.getRoleName());
            resultMap.put("roleDesc", role.getRoleDesc());
            requestResult.status = 201;
            requestResult.msg = "添加角色成功";
            return requestResult.getResult();
        }
        requestResult.data = null;
        requestResult.status = 203;
        requestResult.msg = "添加角色失败";
        return requestResult.getResult();
    }



    @GetMapping("/roles/{roleId}")
    public String getRole(@PathVariable("roleId") Integer roleId){
        Role role = roleService.findOneRole(roleId);
        if (role == null){
            requestResult.msg = "查询角色失败 请确认角色ID是否存在";
            requestResult.status = 404;
            requestResult.data = null;
            return requestResult.getResult();
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("roleId", role.getRoleId());
        resultMap.put("roleName", role.getRoleName());
        resultMap.put("roleDesc", role.getRoleDesc());
        requestResult.status = 200;
        requestResult.msg = "查询角色成功";
        return requestResult.getResult();
    }

    @PutMapping("/roles/{roleId}")
    public String editRoleInfo(@PathVariable("roleId") Integer roleId, @RequestBody Role role){
        role.setRoleId(roleId);
        int i = roleService.editRole(role);
        if (i != 1){
            requestResult.msg = "更新角色信息失败";
            requestResult.status = 404;
            requestResult.data = null;
            return requestResult.getResult();
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("roleId", role.getRoleId());
        resultMap.put("roleName", role.getRoleName());
        resultMap.put("roleDesc", role.getRoleDesc());
        requestResult.status = 200;
        requestResult.msg = "更新角色信息成功";
        return requestResult.getResult();
    }


    @DeleteMapping("/roles/{roleId}")
    public String deleteRole(@PathVariable("roleId") Integer roleId){
        int i = roleService.deleteRole(roleId);
        requestResult.data = null;
        requestResult.msg = "删除成功";
        if (i == 0){
            requestResult.msg = "删除失败";
            requestResult.status = 404;
        }
        return requestResult.getResult();
    }

    @PostMapping("/roles/{roleId}/rights")
    public String authorizationRoles(@PathVariable("roleId") Integer roleId, @RequestBody Map<String, String> params){
        Role role = new Role();
        role.setRoleId(roleId);
        role.setRoleIds(params.get("rids"));
        int i = roleService.editRole(role);
        if (i == 1){
            requestResult.msg = "更新成功";
            requestResult.data = null;
            return requestResult.getResult();
        }
        requestResult.status = 304;
        requestResult.msg = "更新失败";
        return requestResult.getResult();

    }


    @DeleteMapping("/roles/{roleId}/rights/{rightId}")
    public String deleteAppointRights(@PathVariable("roleId") Integer roleId, @PathVariable("rightId") Integer delRightsId){
        Role role = roleService.findOneRole(roleId);

        List<String> roleIds = new ArrayList<>(Arrays.asList(role.getRoleIds().split(",")));
        // 删除指定ID
        roleIds.remove(String.valueOf(delRightsId));
        String idsStr = String.join(",", roleIds);
        int updatePermission = roleService.updatePermission(roleId, idsStr);
        if (updatePermission != 1){
            requestResult.data = null;
            requestResult.msg = "删除权限失败";
            requestResult.status = 302;
            return requestResult.getResult();
        }
        Map<Integer, Menus> screenPermissions = roleService.screenPermissions(roleIds);


        requestResult.data = MenusJson.permissionsErgodicJSON(screenPermissions);
        requestResult.msg = "删除权限成功";

        return requestResult.getResult();
    }


}
