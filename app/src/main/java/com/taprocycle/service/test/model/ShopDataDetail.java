package com.taprocycle.service.test.model;

public class ShopDataDetail {
    private String item_name;
    private String item_id;

    public ShopDataDetail(String item_id, String item_name) {
        this.item_id = item_id;
        this.item_name = item_name;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getItem_id() {
        return item_id;
    }
}
