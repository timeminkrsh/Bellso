package com.taprocycle.service.test.model;

public class GlobalVariable {
    private String address;
    private String name;
    private String pincode;
    public GlobalVariable(String address, String name, String pincode) {
        this.address = address;
        this.name = name;
        this.pincode = pincode;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
