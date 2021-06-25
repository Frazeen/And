package com.example.electrofix;

public class Request {

    String user_name, area, phone, skill, status;

    public Request(String name, String area, String phone, String skill, String status) {
        this.user_name=name;
        this.area=area;
        this.phone=phone;
        this.skill=skill;
        this.status=status;
    }


    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
