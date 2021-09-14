package com.wangd.service;

import com.wangd.pojo.Manager;

import java.util.List;

/**
 * @author wangd
 */
public interface ManagerService {
    public Manager login(String username, String password);

    public List<Manager> queryAllManager(String queryParam, Integer pageNumber, Integer pageSize);
    public int addManager(Manager manager);
}
