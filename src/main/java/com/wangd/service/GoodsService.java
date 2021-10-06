package com.wangd.service;

import com.wangd.pojo.Goods;
import com.wangd.pojo.GoodsAttribute;
import com.wangd.pojo.GoodsCategory;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;

/**
 * @author wangd
 */
public interface GoodsService {

    public List<Goods> getGoodsList(String queryParam, Integer currentPageNumber, Integer pageSize);

    public int getGoodsTotal();

    public Map<Integer, GoodsCategory> getGoodsCategory(@Nullable String type);

    public GoodsCategory getGoodsOneCategory(Integer categoriesId);

    public int addGoodsCategory(GoodsCategory goodsCategory);

    public int editGoodsCategory(GoodsCategory goodsCategory);

    public List<GoodsAttribute> getGoodsAttributes(Integer categoryId);

    public int addGoodsAttribute(GoodsAttribute goodsAttribute);

    public int softDeleteAttribute(Integer categoryId, Integer attributeId);

    public GoodsAttribute getGoodsAttribute(Integer categoryId, Integer attributeId);

    public int editAttribute(GoodsAttribute goodsAttribute);

}
