package com.wangd.service;

import com.wangd.pojo.Menus;
import com.wangd.pojo.Role;

import java.util.List;
import java.util.Map;

/**
 * @author wangd
 */
public interface RoleService {

    public List<Role> getRoles();

    public Map<Integer, Menus> getPermissions();

    public Map<Integer, Menus> screenPermissions(List<String> ids);

    // 根据ID删除当前权限
    public Map<Integer, Menus> deletePermissions(String[] ids, Integer delRoleId);

    public int updatePermission(Integer roleId, String roleIds);

    public List<Menus> getPermissionsList();

    public int addRole(Role role);

    public Role findOneRole(Integer roleId);

    public int editRole(Role role);

    public int deleteRole(Integer roleId);
}
