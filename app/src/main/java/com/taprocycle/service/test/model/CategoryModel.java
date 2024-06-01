package com.taprocycle.service.test.model;

public class CategoryModel {

    private String cat_id,web_title,web_image,subcategoryname,scid,sub_wallet,sub_walletamount;
    private String category_name;
    private String category_id;

    public String getSubcategoryname() {
        return subcategoryname;
    }

    public void setSubcategoryname(String subcategoryname) {
        this.subcategoryname = subcategoryname;
    }

    public String getScid() {
        return scid;
    }

    public void setScid(String scid) {
        this.scid = scid;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getWeb_title() {
        return web_title;
    }

    public void setWeb_title(String web_title) {
        this.web_title = web_title;
    }

    public String getWeb_image() {
        return web_image;
    }

    public void setWeb_image(String web_image) {
        this.web_image = web_image;
    }
    public String getCategory_id() {
        return this.category_id;
    }

    public String getSub_wallet() {
        return sub_wallet;
    }

    public void setSub_wallet(String sub_wallet) {
        this.sub_wallet = sub_wallet;
    }

    public String getSub_walletamount() {
        return sub_walletamount;
    }

    public void setSub_walletamount(String sub_walletamount) {
        this.sub_walletamount = sub_walletamount;
    }

    public String getCategory_name() {
        return this.category_name;
    }

}
