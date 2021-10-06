package com.wangd.dao;

import com.wangd.pojo.Goods;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * @author wangd
 */
public interface GoodsDAO {
    public List<Goods> queryAllGoods(@Param("searchParam")String search, @Param("currentPage") Integer currentPage, @Param("pageSize") Integer pageSize);

    public Integer queryGoodsTotal();
}
