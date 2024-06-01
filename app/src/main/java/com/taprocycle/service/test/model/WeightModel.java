package com.taprocycle.service.test.model;

import android.graphics.Bitmap;

public class WeightModel {
    private String qty,scid,size,color,stock,offer_id,offer_name,discount,discount_type,special_discount,special_discount_type,web_title, web_price, wid, points, option_role, mrp;
    private String image;
    private String color_image;
    private String color_name;

    public String getColor_image() {
        return color_image;
    }

    public void setColor_image(String color_image) {
        this.color_image = color_image;
    }

    public String getColor_name() {
        return color_name;
    }

    public void setColor_name(String color_name) {
        this.color_name = color_name;
    }

    public WeightModel() {

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getScid() {
        return scid;
    }

    public void setScid(String scid) {
        this.scid = scid;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public WeightModel(String web_title, int logo) {
        this.web_title = web_title;
    }



    public String getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(String offer_id) {
        this.offer_id = offer_id;
    }

    public String getOffer_name() {
        return offer_name;
    }

    public void setOffer_name(String offer_name) {
        this.offer_name = offer_name;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscount_type() {
        return discount_type;
    }

    public void setDiscount_type(String discount_type) {
        this.discount_type = discount_type;
    }

    public String getSpecial_discount() {
        return special_discount;
    }

    public void setSpecial_discount(String special_discount) {
        this.special_discount = special_discount;
    }

    public String getSpecial_discount_type() {
        return special_discount_type;
    }

    public void setSpecial_discount_type(String special_discount_type) {
        this.special_discount_type = special_discount_type;
    }

    public String getWeb_title() {
        return web_title;
    }

    public void setWeb_title(String web_title) {
        this.web_title = web_title;
    }

    public String getWeb_price() {
        return web_price;
    }

    public void setWeb_price(String web_price) {
        this.web_price = web_price;
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getOption_role() {
        return option_role;
    }

    public void setOption_role(String option_role) {
        this.option_role = option_role;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }
}
