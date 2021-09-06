package com.wangd.service.impl;

import com.wangd.dao.ManagerDAO;
import com.wangd.pojo.Manager;
import com.wangd.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangd
 */
@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    private ManagerDAO managerDAO;
    @Override
    public Manager login(String username, String password) {

        return managerDAO.queryManager(username, password);
    }
}
