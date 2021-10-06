package com.wangd.dao;

import com.wangd.pojo.GoodsCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangd
 */
public interface GoodsCategoryDAO {
    public List<GoodsCategory> queryAllGoodsCategory();

    public GoodsCategory queryOneGoodsCategory(@Param("categoryId") Integer categoriesId);

    public int insertCategories(GoodsCategory goodsCategory);

    public int updateCategories(GoodsCategory goodsCategory);

    public int deleteByCategoryId(@Param("categoryId") Integer categoryId);
}
