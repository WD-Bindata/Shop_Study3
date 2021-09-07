package com.wangd.dao;

import com.wangd.pojo.Menus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangd
 */
public interface MenusDAO {
    public List<Menus> getMenus();

    public List<Menus> getChildrenMenus(@Param("menuId") Integer menuId);
}
