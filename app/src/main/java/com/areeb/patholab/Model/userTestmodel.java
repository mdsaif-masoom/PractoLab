package com.areeb.patholab.Model;

public class userTestmodel {

    String Fullname ,Phone,UserEmail;

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public userTestmodel(String fullname, String phone, String userEmail) {
        Fullname = fullname;
        Phone = phone;
        UserEmail = userEmail;
    }
}
