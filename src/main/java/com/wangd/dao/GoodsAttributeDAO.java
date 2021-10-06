package com.wangd.dao;

import com.wangd.pojo.GoodsAttribute;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangd
 */
public interface GoodsAttributeDAO {
    public List<GoodsAttribute> queryByCategoryId(@Param("categoryId") Integer categoryid);

    public int insertGoodsAttribute(GoodsAttribute goodsAttribute);

    public int deleteAttributeByCategoryIdAndAttributeId(@Param("categoryId") Integer categoryId, @Param("attributeId") Integer attributeId);
    public GoodsAttribute queryAttributeByCategoryIdAndAttributeId(@Param("categoryId") Integer categoryId, @Param("attributeId") Integer attributeId);

    public int updateAttribute(GoodsAttribute goodsAttribute);
}
