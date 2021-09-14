package com.wangd.dao;

import com.wangd.pojo.Manager;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;

import java.util.List;


/**
 * @author wangd
 */

public interface ManagerDAO {
    public Manager queryManager(@Param("username") String username, @Param("password") String password);
    public List<Manager> byUsernameQueryManager(@Param("queryParam") String queryParam, @Param("pageNumber") Integer pageNumber, @Param("pageSize") Integer pageSize);
    public int insertManager(Manager manager);
}
