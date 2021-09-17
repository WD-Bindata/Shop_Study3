package com.wangd.service;

import com.wangd.pojo.Manager;

import java.util.List;

/**
 * @author wangd
 */
public interface ManagerService {
    // 登录
    public Manager login(String username, String password);

    // 查询所有的Manager
    public List<Manager> queryAllManager(String queryParam, Integer pageNumber, Integer pageSize);

    // 获取所有的manager条数
    public int getCount();

    // 添加manager
    public int addManager(Manager manager);

    // 根据ID查询manager
    public Manager queryById(Integer managerId);

    // 更新账户状态
    public Manager editState(Integer userid, Integer state);

    // 更新Manager
    public Manager editManager(Manager manager);

    public int deleteById(Integer userid);
}
