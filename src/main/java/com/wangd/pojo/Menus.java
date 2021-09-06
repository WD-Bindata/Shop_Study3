package com.wangd.pojo;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * @author wangd
 */
@Alias("menus")
public class Menus implements Serializable {
    private Integer id;
    private String apiPath;
    private String apiAction;
    private Integer apiOrder;
    private Integer menuId;
    private Integer fatherMenuId;
    private String menuName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApiPath() {
        return apiPath;
    }

    public void setApiPath(String apiPath) {
        this.apiPath = apiPath;
    }

    public String getApiAction() {
        return apiAction;
    }

    public void setApiAction(String apiAction) {
        this.apiAction = apiAction;
    }

    public Integer getApiOrder() {
        return apiOrder;
    }

    public void setApiOrder(Integer apiOrder) {
        this.apiOrder = apiOrder;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getFatherMenuId() {
        return fatherMenuId;
    }

    public void setFatherMenuId(Integer fatherMenuId) {
        this.fatherMenuId = fatherMenuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    @Override
    public String toString() {
        return "Menus{" +
                "id=" + id +
                ", apiPath='" + apiPath + '\'' +
                ", apiAction='" + apiAction + '\'' +
                ", apiOrder=" + apiOrder +
                ", menuId=" + menuId +
                ", fatherMenuId=" + fatherMenuId +
                ", menuName='" + menuName + '\'' +
                '}';
    }
}
