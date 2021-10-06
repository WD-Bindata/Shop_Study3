package com.wangd.service.impl;

import com.wangd.dao.GoodsAttributeDAO;
import com.wangd.dao.GoodsCategoryDAO;
import com.wangd.dao.GoodsDAO;
import com.wangd.pojo.Goods;
import com.wangd.pojo.GoodsAttribute;
import com.wangd.pojo.GoodsCategory;
import com.wangd.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangd
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsDAO goodsDAO;

    @Autowired
    private GoodsCategoryDAO categoryDAO;

    @Autowired
    private GoodsAttributeDAO attributeDAO;

    @Override
    public List<Goods> getGoodsList(String queryParam, Integer currentPageNumber, Integer pageSize) {
        if (currentPageNumber == 1){
            currentPageNumber = 0;
        }else {
            currentPageNumber = (currentPageNumber * pageSize) - pageSize + 1;
        }
        return goodsDAO.queryAllGoods(queryParam, currentPageNumber, pageSize);
    }

    @Override
    public int getGoodsTotal() {
        return goodsDAO.queryGoodsTotal();
    }


    @Override
    public Map<Integer, GoodsCategory> getGoodsCategory(String type) {

        List<GoodsCategory> goodsCategoryList = categoryDAO.queryAllGoodsCategory();

        goodsCategoryList.sort((o1, o2) -> {
            if (o1.getCategoryLevel() < o2.getCategoryLevel()){
                return -1;
            }
            return 0;

        });
        // 存放一级分类 及其子(二级)分类
        Map<Integer, GoodsCategory> resultCategory = new HashMap<>();
        // 存放二级分类，及其三级分类
        Map<Integer, GoodsCategory> category2 = new HashMap<>();

        for (GoodsCategory goodsCategory : goodsCategoryList) {
            if (goodsCategory.getCategoryLevel() == 0){
                resultCategory.put(goodsCategory.getId(), goodsCategory);
            } else if (goodsCategory.getCategoryLevel() == 1){
                category2.put(goodsCategory.getId(), goodsCategory);
            }
        }
        for (GoodsCategory category3 : goodsCategoryList) {
            Integer categoryFatherId = category3.getCategoryFatherId();
            if (categoryFatherId == null || !category2.containsKey(category3.getCategoryFatherId())){
//                category2.put(category3.getId(), category3);
                continue;
            }
            // 将二级商品标签与二级商品标签进行组装
            GoodsCategory goodsCategory2 = category2.get(category3.getCategoryFatherId());
            goodsCategory2.getChildren().add(category3);
        }


        category2.forEach((key, goodsCategory2) -> {
            Integer categoryFatherId = goodsCategory2.getCategoryFatherId();
            if (resultCategory.containsKey(categoryFatherId)){
                GoodsCategory goodsCategory1 = resultCategory.get(categoryFatherId);
                goodsCategory1.getChildren().add(goodsCategory2);
            }
        });
        return resultCategory;
    }

    @Override
    public GoodsCategory getGoodsOneCategory(Integer categoriesId) {
        return categoryDAO.queryOneGoodsCategory(categoriesId);
    }

    @Transactional
    @Override
    public int addGoodsCategory(GoodsCategory goodsCategory) {
        return categoryDAO.insertCategories(goodsCategory);
    }

    @Transactional
    @Override
    public int editGoodsCategory(GoodsCategory goodsCategory) {
        return categoryDAO.updateCategories(goodsCategory);
    }

    @Override
    public List<GoodsAttribute> getGoodsAttributes(Integer categoryId) {
        return attributeDAO.queryByCategoryId(categoryId);
    }

    @Override
    public int addGoodsAttribute(GoodsAttribute goodsAttribute) {
        return attributeDAO.insertGoodsAttribute(goodsAttribute);
    }

    @Override
    public int softDeleteAttribute(Integer categoryId, Integer attributeId) {
        return attributeDAO.deleteAttributeByCategoryIdAndAttributeId(categoryId, attributeId);
    }

    @Override
    public GoodsAttribute getGoodsAttribute(Integer categoryId, Integer attributeId) {
        return attributeDAO.queryAttributeByCategoryIdAndAttributeId(categoryId, attributeId);
    }

    @Override
    public int editAttribute(GoodsAttribute goodsAttribute) {
        return attributeDAO.updateAttribute(goodsAttribute);
    }
}
