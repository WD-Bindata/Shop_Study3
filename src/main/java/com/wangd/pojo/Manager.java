package com.wangd.pojo;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * @author wangd
 */
@Alias("manager")
public class Manager implements Serializable {
    private Integer id;
    private String username;
    private String password;
    private Integer registrationTime;
    private Integer roleId;
    private String mobile;
    private String email;
    private Integer state;
    private Integer type;
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Integer registrationTime) {
        this.registrationTime = registrationTime;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", registrationTime=" + registrationTime +
                ", roleId=" + roleId +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", state=" + state +
                ", type=" + type +
                ", role=" + role +
                '}';
    }
}
