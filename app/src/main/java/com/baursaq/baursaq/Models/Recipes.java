package com.baursaq.baursaq.Models;

public class Recipes {
    private String rname, rdescription, rimage, rId, rDate, rTime;
    public Recipes(){

    }

    public Recipes(String rname, String rdescription, String rimage, String rId, String rDate, String rTime) {
        this.rname = rname;
        this.rdescription = rdescription;
        this.rimage = rimage;
        this.rId = rId;
        this.rDate = rDate;
        this.rTime = rTime;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getRdescription() {
        return rdescription;
    }

    public void setRdescription(String rdescription) {
        this.rdescription = rdescription;
    }

    public String getRimage() {
        return rimage;
    }

    public void setRimage(String rimage) {
        this.rimage = rimage;
    }

    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId;
    }

    public String getrDate() {
        return rDate;
    }

    public void setrDate(String rDate) {
        this.rDate = rDate;
    }

    public String getrTime() {
        return rTime;
    }

    public void setrTime(String rTime) {
        this.rTime = rTime;
    }
}
