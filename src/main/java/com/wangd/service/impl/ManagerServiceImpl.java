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
    public int getCount() {
        return managerDAO.getManagerCount();
    }

    @Override
    public int addManager(Manager manager) {
        return managerDAO.insertManager(manager);
    }

    @Override
    public Manager queryById(Integer managerId) {
        return managerDAO.queryById(managerId);
    }

    @Override
    public Manager editState(Integer userid, Integer state){
        int number_of_affected = managerDAO.updateState(userid, state);
        Manager manager = null;
        // 判断影响条数
        if (number_of_affected == 1){
            manager = managerDAO.queryById(userid);
        }
        return manager;
    }

    @Override
    public Manager editManager(Manager manager) {
        int i = managerDAO.updateManager(manager);
        Manager managerResult = null;
        if (i == 1){
            managerResult = managerDAO.queryById(manager.getId());
        }
        return managerResult;
    }

    @Override
    public int deleteById(Integer userid) {
        return managerDAO.deleteById(userid);
    }
}
