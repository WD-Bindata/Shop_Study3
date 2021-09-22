package com.wangd.dao;

import com.wangd.pojo.Menus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangd
 */
public interface MenusDAO {
    public List<Menus> queryAllMenus();

    public List<Menus> queryByChildren(@Param("menuId") Integer menuId);
}
