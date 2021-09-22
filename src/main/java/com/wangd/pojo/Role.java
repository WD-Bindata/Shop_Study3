package com.wangd.pojo;

import org.apache.ibatis.type.Alias;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangd
 */
@Alias("role")
public class Role {
    private Integer roleId;
    private String roleName;
    private String roleIds;
    private String controllerOperation;
    private String roleDesc;
    private List<Menus> menusList = new ArrayList<>();

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public String getControllerOperation() {
        return controllerOperation;
    }

    public void setControllerOperation(String controllerOperation) {
        this.controllerOperation = controllerOperation;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public List<Menus> getMenusList() {
        return menusList;
    }

    public void setMenusList(List<Menus> menusList) {
        this.menusList = menusList;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", roleIds='" + roleIds + '\'' +
                ", controllerOperation='" + controllerOperation + '\'' +
                ", roleDesc='" + roleDesc + '\'' +
                ", menusList=" + menusList +
                '}';
    }
}
