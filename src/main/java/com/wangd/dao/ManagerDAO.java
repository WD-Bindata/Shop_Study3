package com.wangd.dao;

import com.wangd.pojo.Manager;
import javafx.beans.DefaultProperty;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;


/**
 * @author wangd
 */

public interface ManagerDAO {
    public Manager queryManager(@Param("username") String username, @Param("password") String password);

    public List<Manager> byUsernameQueryManager(@Nullable @Param("queryParam") String queryParam, @Param("pageNumber") Integer pageNumber, @Param("pageSize") Integer pageSize);

    public int getManagerCount();

    public int insertManager(Manager manager);

    public Manager queryById(@Param("managerId") Integer managerId);

    public int updateState(@Param("userid") Integer userid, @Param("state") Integer state);

    public int updateManager(@Param("manager") Manager manager);

    public int deleteById(@Param("userid") Integer userid);

    public Manager queryManagerAndRoleById(@Param("managerId") Integer managerId);


}
