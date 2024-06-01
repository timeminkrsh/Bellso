package com.taprocycle.service.test.model;

public class RecendlyModel {
    String referencedate,number,Amount,status,remarks;

    public RecendlyModel(String referencedate,String number,String Amount , String status,String remarks){
        this.referencedate = referencedate;
        this.number = number;
        this.Amount =Amount;
        this.status=status;
        this.remarks=remarks;
    }

    public RecendlyModel() {

    }

    public String getReferencedate() {
        return referencedate;
    }

    public void setReferencedate(String referencedate) {
        this.referencedate = referencedate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
