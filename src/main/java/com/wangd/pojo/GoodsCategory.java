package com.wangd.pojo;

import org.apache.ibatis.type.Alias;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangd
 */
// 商品类型
@Alias("goodsCategory")
public class GoodsCategory {
    // 分类ID
    private Integer id;
    private String categoryName;
    private Integer categoryFatherId;
    private Integer categoryLevel;
    private Integer isDelete;
    private String categoryIcon;
    private String categorySrc;

    private List<GoodsCategory> children = new ArrayList<>();

    public GoodsCategory(Integer id, String categoryName, Integer categoryFatherId,
                         Integer categoryLevel, Integer isDelete, String categoryIcon,
                         String categorySrc) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryFatherId = categoryFatherId;
        this.categoryLevel = categoryLevel;
        this.isDelete = isDelete;
        this.categoryIcon = categoryIcon;
        this.categorySrc = categorySrc;
    }

    public GoodsCategory() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCategoryFatherId() {
        return categoryFatherId;
    }

    public void setCategoryFatherId(Integer categoryFatherId) {
        this.categoryFatherId = categoryFatherId;
    }

    public Integer getCategoryLevel() {
        return categoryLevel;
    }

    public void setCategoryLevel(Integer categoryLevel) {
        this.categoryLevel = categoryLevel;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(String categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    public String getCategorySrc() {
        return categorySrc;
    }

    public void setCategorySrc(String categorySrc) {
        this.categorySrc = categorySrc;
    }

    public List<GoodsCategory> getChildren() {
        return children;
    }

    public void setChildren(List<GoodsCategory> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "GoodsCategory{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", categoryFatherId=" + categoryFatherId +
                ", categoryLevel=" + categoryLevel +
                ", isDelete=" + isDelete +
                ", categoryIcon='" + categoryIcon + '\'' +
                ", categorySrc='" + categorySrc + '\'' +
                ", children=" + children +
                '}';
    }
}
