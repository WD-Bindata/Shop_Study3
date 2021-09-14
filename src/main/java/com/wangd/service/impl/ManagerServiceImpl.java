package com.wangd.service.impl;

import com.wangd.dao.ManagerDAO;
import com.wangd.pojo.Manager;
import com.wangd.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<Manager> queryAllManager(String queryParam, Integer currPage, Integer pageSize) {
        if (currPage == 1){
            currPage = 0;
        }
        List<Manager> managerList = managerDAO.byUsernameQueryManager(queryParam, currPage, pageSize);

        return managerList;
    }

    @Override
    public int addManager(Manager manager) {
        return managerDAO.insertManager(manager);
    }
}
