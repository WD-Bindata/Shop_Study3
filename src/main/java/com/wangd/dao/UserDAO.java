package com.wangd.dao;

import com.wangd.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangd
 */
public interface UserDAO {
    public int insert(User user);
    public User selectByUsername(@Param("username") String username,@Param("password") String password);


}
