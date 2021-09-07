package com.wangd.pojo;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private List<Menus> children = new ArrayList<>();

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

    public List<Menus> getChildren() {
        return children;
    }

    public void setChildren(List<Menus> children) {
        this.children = children;
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
                ", children=" + children +
                '}';
    }
}
