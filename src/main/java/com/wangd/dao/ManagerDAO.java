package com.wangd.dao;

import com.wangd.pojo.Manager;
import org.apache.ibatis.annotations.Param;


/**
 * @author wangd
 */

public interface ManagerDAO {
    public Manager queryManager(@Param("username") String username, @Param("password") String password);
}
