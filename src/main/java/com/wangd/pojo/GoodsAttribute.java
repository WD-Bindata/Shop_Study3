package com.wangd.pojo;

import org.apache.ibatis.type.Alias;

/**
 * @author wangd
 */

@Alias("goodsAttribute")
public class GoodsAttribute {
    private Integer attributeId;
    private String attributeName;
    private Integer categoryId;
    private String attributeSelect;
    private String attributeWrite;
    private String attributeValues;
    private Integer deleteTime;

    public Integer getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Integer attributeId) {
        this.attributeId = attributeId;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getAttributeSelect() {
        return attributeSelect;
    }

    public void setAttributeSelect(String attributeSelect) {
        this.attributeSelect = attributeSelect;
    }

    public String getAttributeWrite() {
        return attributeWrite;
    }

    public void setAttributeWrite(String attributeWrite) {
        this.attributeWrite = attributeWrite;
    }

    public String getAttributeValues() {
        return attributeValues;
    }

    public void setAttributeValues(String attributeValues) {
        this.attributeValues = attributeValues;
    }

    public Integer getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Integer deleteTime) {
        this.deleteTime = deleteTime;
    }

    @Override
    public String toString() {
        return "GoodsAttribute{" +
                "attributeId=" + attributeId +
                ", attributeName='" + attributeName + '\'' +
                ", categoryId=" + categoryId +
                ", attributeSelect='" + attributeSelect + '\'' +
                ", attributeWrite='" + attributeWrite + '\'' +
                ", attributeValues='" + attributeValues + '\'' +
                ", deleteTime=" + deleteTime +
                '}';
    }
}
