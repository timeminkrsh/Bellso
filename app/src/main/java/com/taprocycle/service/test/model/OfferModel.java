package com.taprocycle.service.test.model;

public class OfferModel {
    private String web_title,subcategory_id,category_id,product_id;
    private  int image;

    public OfferModel(String for_the_perfect_birthday_celebration, String s, String s1, String s2, int logo) {
        this.web_title=for_the_perfect_birthday_celebration;
        this.image=logo;
    }

    public String getSubcategory_id() {
        return subcategory_id;
    }

    public void setSubcategory_id(String subcategory_id) {
        this.subcategory_id = subcategory_id;
    }

    public String getWeb_title() {
        return web_title;
    }

    public void setWeb_title(String web_title) {
        this.web_title = web_title;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
