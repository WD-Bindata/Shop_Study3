package com.wangd.dao;

import com.wangd.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangd
 */
public interface RoleDAO {
    public List<Role> queryAllRole();
    public int addRole(Role role);

    public Role queryOneRole(@Param("roleId") Integer roleId);

    public int updateRole(Role role);

    public int deleteByRoleId(@Param("roleId") Integer roleId);

    public int updateRoleIds(@Param("roleId") Integer roleId, @Param("roleIds") String IdsStr);
}
