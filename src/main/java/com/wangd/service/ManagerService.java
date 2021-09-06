package com.wangd.service;

import com.wangd.pojo.Manager;

/**
 * @author wangd
 */
public interface ManagerService {
    public Manager login(String username, String password);
}
