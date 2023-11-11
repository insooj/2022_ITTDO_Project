package com.example.caregiverapp;

public class PatientAccount {
    private String userName;//이름
    private String userPhone;
    private String userRegion;// 지역
    private String userold;// 나이
    private String userdate;// 날짜
    private String usertime;// 시간
    private String userdisease;// 경력
    private Double userpay;
    private String etc;
    private String sex;

    public PatientAccount(){}


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserRegion() {
        return userRegion;
    }

    public void setUserRegion(String userRegion) {
        this.userRegion = userRegion;
    }

    public String getUserold() {
        return userold;
    }

    public void setUserold(String userold) {
        this.userold = userold;
    }

    public String getUserdate() {
        return userdate;
    }

    public void setUserdate(String userdate) {
        this.userdate = userdate;
    }

    public String getUsertime() {
        return usertime;
    }

    public void setUsertime(String usertime) {
        this.usertime = usertime;
    }

    public String getUserdisease() {
        return userdisease;
    }

    public void setUserdisease(String userdisease) {
        this.userdisease = userdisease;
    }

    public Double getUserpay() {
        return userpay;
    }

    public void setUserpay(Double userpay) {
        this.userpay = userpay;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }
}
