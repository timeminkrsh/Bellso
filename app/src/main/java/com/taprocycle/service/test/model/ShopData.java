package com.taprocycle.service.test.model;

import java.util.ArrayList;

public class ShopData {
    private String web_title;
    private String web_image;
    private ArrayList<ShopDataDetail> ch_list;
    public ShopData() {
        this.web_title = "";
        this.web_image = "";
        this.ch_list = new ArrayList<>();
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

    public ArrayList<ShopDataDetail> getCh_list() {
        return ch_list;
    }

    public void setCh_list(ArrayList<ShopDataDetail> ch_list) {
        this.ch_list = ch_list;
    }
}
