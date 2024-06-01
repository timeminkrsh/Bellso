package com.taprocycle.service.test.model;

public class HelpModel {

    private String tkno,date,complaint,naturecomplaint,details,Attachment,Status;

    public HelpModel(String tkno, String date, String complaint, String naturecomplaint, String details, String Attachment,String Status ) {

        this.tkno=tkno;
        this.date=date;
        this.complaint=complaint;
        this.naturecomplaint=naturecomplaint;
        this.details=details;
        this.Attachment=Attachment;
        this.Status=Status;

    }


    public String getTkno() {
        return tkno;
    }

    public void setTkno(String tkno) {
        this.tkno = tkno;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getNaturecomplaint() {
        return naturecomplaint;
    }

    public void setNaturecomplaint(String naturecomplaint) {
        this.naturecomplaint = naturecomplaint;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getAttachment() {
        return Attachment;
    }

    public void setAttachment(String attachment) {
        Attachment = attachment;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
