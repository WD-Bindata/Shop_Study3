package com.wangd.pojo;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * @author wangd
 */
@Alias("goods")
public class Goods implements Serializable {
    private Integer goodsId;
    private String goodsName;
    private Float goodsPrice;
    private Integer goodsNumber;
    private Float goodsWeight;
    private Integer goodsTypeId;
    private String goodsIntroduce;
    private String goodsBigPicture;
    private String goodsSmallPicture;
    private String isDel;
    private Integer addTime;
    private Integer updateTime;
    private Integer deleteTime;
    // 一级分类
    private Integer oneClassificationId;
    // 二级分类
    private Integer towClassificationId;
    // 三级分类
    private Integer threeClassificationId;
    // 热销
    private Integer hotQuantity;
    // 是否促销
    private Integer isPromote;
    private Integer goodsState;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Float getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Float goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public Float getGoodsWeight() {
        return goodsWeight;
    }

    public void setGoodsWeight(Float goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public Integer getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(Integer goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }

    public String getGoodsIntroduce() {
        return goodsIntroduce;
    }

    public void setGoodsIntroduce(String goodsIntroduce) {
        this.goodsIntroduce = goodsIntroduce;
    }

    public String getGoodsBigPicture() {
        return goodsBigPicture;
    }

    public void setGoodsBigPicture(String goodsBigPicture) {
        this.goodsBigPicture = goodsBigPicture;
    }

    public String getGoodsSmallPicture() {
        return goodsSmallPicture;
    }

    public void setGoodsSmallPicture(String goodsSmallPicture) {
        this.goodsSmallPicture = goodsSmallPicture;
    }

    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

    public Integer getAddTime() {
        return addTime;
    }

    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Integer deleteTime) {
        this.deleteTime = deleteTime;
    }

    public Integer getOneClassificationId() {
        return oneClassificationId;
    }

    public void setOneClassificationId(Integer oneClassificationId) {
        this.oneClassificationId = oneClassificationId;
    }

    public Integer getTowClassificationId() {
        return towClassificationId;
    }

    public void setTowClassificationId(Integer towClassificationId) {
        this.towClassificationId = towClassificationId;
    }

    public Integer getThreeClassificationId() {
        return threeClassificationId;
    }

    public void setThreeClassificationId(Integer threeClassificationId) {
        this.threeClassificationId = threeClassificationId;
    }

    public Integer getHotQuantity() {
        return hotQuantity;
    }

    public void setHotQuantity(Integer hotQuantity) {
        this.hotQuantity = hotQuantity;
    }

    public Integer getIsPromote() {
        return isPromote;
    }

    public void setIsPromote(Integer isPromote) {
        this.isPromote = isPromote;
    }

    public Integer getGoodsState() {
        return goodsState;
    }

    public void setGoodsState(Integer goodsState) {
        this.goodsState = goodsState;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsPrice=" + goodsPrice +
                ", goodsNumber=" + goodsNumber +
                ", goodsWeight=" + goodsWeight +
                ", goodsTypeId=" + goodsTypeId +
                ", goodsIntroduce='" + goodsIntroduce + '\'' +
                ", goodsBigPicture='" + goodsBigPicture + '\'' +
                ", goodsSmallPicture='" + goodsSmallPicture + '\'' +
                ", isDel='" + isDel + '\'' +
                ", addTime=" + addTime +
                ", updateTime=" + updateTime +
                ", deleteTime=" + deleteTime +
                ", oneClassificationId=" + oneClassificationId +
                ", towClassificationId=" + towClassificationId +
                ", threeClassificationId=" + threeClassificationId +
                ", hotQuantity=" + hotQuantity +
                ", isPromote=" + isPromote +
                ", goodsState=" + goodsState +
                '}';
    }
}
