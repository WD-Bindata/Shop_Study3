package com.wangd.enums;

/**
 * @author wangd
 */
public enum SexEnum {
    MAN("男"),
    FEM("女");

    private String sex;

    SexEnum(String s){

        this.sex=s;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
