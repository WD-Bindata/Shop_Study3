package com.wangd.pojo;

/**
 * @author wangd
 */
public class Role {
    private Integer roleId;
    private String roleName;
    private String roleIds;
    private String controllerOperation;
    private String roleDesc;

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

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", roleIds='" + roleIds + '\'' +
                ", controllerOperation='" + controllerOperation + '\'' +
                ", roleDesc='" + roleDesc + '\'' +
                '}';
    }
}
