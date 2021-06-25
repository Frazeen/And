package com.example.electrofix;

import com.google.gson.annotations.SerializedName;

public class RequestList {

    @SerializedName("req_id")
    private int req_id;

    @SerializedName("cus_id")
    private int cus_id;

    @SerializedName("cus_name")
    private String cus_name;

    @SerializedName("cus_phone")
    private String cus_phone;

    @SerializedName("rep_id")
    private int rep_id;

    @SerializedName("rep_name")
    private String rep_name;

    @SerializedName("rep_phone")
    private String rep_phone;

    @SerializedName("amount")
    private String amount;

    @SerializedName("status")
    private String status;

    public int getReq_id() {
        return req_id;
    }

    public void setReq_id(int req_id) {
        this.req_id = req_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCus_id() {
        return cus_id;
    }

    public void setCus_id(int cus_id) {
        this.cus_id = cus_id;
    }

    public int getRep_id() {
        return rep_id;
    }

    public void setRep_id(int rep_id) {
        this.rep_id = rep_id;
    }

    public String getRep_name() {
        return rep_name;
    }

    public void setRep_name(String rep_name) {
        this.rep_name = rep_name;
    }

    public String getRep_phone() {
        return rep_phone;
    }

    public void setRep_phone(String rep_phone) {
        this.rep_phone = rep_phone;
    }

    public String getCus_name() {
        return cus_name;
    }

    public void setCus_name(String cus_name) {
        this.cus_name = cus_name;
    }

    public String getCus_phone() {
        return cus_phone;
    }

    public void setCus_phone(String cus_phone) {
        this.cus_phone = cus_phone;
    }


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}


