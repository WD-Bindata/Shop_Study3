package com.wangd.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangd.pojo.Goods;
import com.wangd.pojo.GoodsAttribute;
import com.wangd.pojo.GoodsCategory;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * @author wangd
 */
public class GoodsResultTemplate {

    public static JSONObject goodsApiMapping(Goods goods){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("goods_id", goods.getGoodsId());
        jsonObject.put("goods_name", goods.getGoodsName());
        jsonObject.put("goods_price", goods.getGoodsPrice());
        jsonObject.put("goods_number", goods.getGoodsNumber());
        jsonObject.put("goods_weight", goods.getGoodsWeight());
        jsonObject.put("goods_state", goods.getGoodsState());
        jsonObject.put("add_time", goods.getAddTime());
        jsonObject.put("upd_time", goods.getUpdateTime());
        jsonObject.put("hot_number", goods.getHotQuantity());
        jsonObject.put("is_promote", goods.getIsPromote());

        return jsonObject;
    }

    public static GoodsAttribute goodsAttributeApiMapToEntity(Map<String, Object> apiMap){
        GoodsAttribute goodsAttribute = new GoodsAttribute();
        goodsAttribute.setAttributeId((Integer) apiMap.get("attr_id"));
        goodsAttribute.setAttributeName((String) apiMap.get("attr_name"));
        goodsAttribute.setAttributeSelect((String) apiMap.get("attr_sel"));
        goodsAttribute.setCategoryId((Integer) apiMap.get("cat_id"));
        goodsAttribute.setAttributeWrite((String) apiMap.get("attr_write"));
        goodsAttribute.setDeleteTime((Integer) apiMap.get("delete_time"));
        goodsAttribute.setAttributeValues((String) apiMap.get("attr_vals"));
        return goodsAttribute;
    }

    public static JSONObject goodsCategoryAPIMapping(GoodsCategory goodsCategory){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cat_id", goodsCategory.getId());
        jsonObject.put("cat_name", goodsCategory.getCategoryName());
        jsonObject.put("cat_pid", goodsCategory.getCategoryFatherId());
        jsonObject.put("cat_level", goodsCategory.getCategoryLevel());
        jsonObject.put("cat_deleted", goodsCategory.getIsDelete() == 1);
        return jsonObject;
    }

    public static JSONObject goodsAttributeAPIMapping(GoodsAttribute goodsAttribute){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("attr_id", goodsAttribute.getAttributeId());
        jsonObject.put("attr_name", goodsAttribute.getAttributeName());
        jsonObject.put("cat_id", goodsAttribute.getCategoryId());
        jsonObject.put("attr_sel", goodsAttribute.getAttributeSelect());
        jsonObject.put("attr_vals", goodsAttribute.getAttributeValues());
        jsonObject.put("attr_write", goodsAttribute.getAttributeWrite());
        jsonObject.put("delete_time", goodsAttribute.getDeleteTime());
        return jsonObject;
    }

    public static JSONArray goodsCategoryToJsonArray(Map<Integer, GoodsCategory> goodsCategoryMap, String treeHierarchy){
        JSONArray result = new JSONArray();
        Collection<GoodsCategory> values = goodsCategoryMap.values();
        for (GoodsCategory goodsCategory1 : values){
            JSONObject apiMapping = GoodsResultTemplate.goodsCategoryAPIMapping(goodsCategory1);
            // 存放2级分类列表
            JSONArray apiMappingArray2 = new JSONArray();
            for (GoodsCategory goodsCategory2 : goodsCategory1.getChildren()) {
                JSONObject apiMapping2 = goodsCategoryAPIMapping(goodsCategory2);
                if ("3".equals(treeHierarchy) || "".equals(treeHierarchy)){
                // 用来存放3级分类
                    JSONArray apiMappingArray3 = new JSONArray();
                    goodsCategory2.getChildren().forEach(goodsCategory3 -> {
                        apiMappingArray3.add(goodsCategoryAPIMapping(goodsCategory3));
                    });
                    apiMapping2.put("children", apiMappingArray3);
                }

                apiMappingArray2.add(apiMapping2);
            }
            apiMapping.put("children", apiMappingArray2);
            result.add(apiMapping);
        }
        return result;
    }
}
