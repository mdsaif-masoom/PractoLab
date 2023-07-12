package com.areeb.patholab.Model;

public class ServiceModel {
    String AdminEmail , LabName ,Phone,otp;

    public String getAdminEmail() {
        return AdminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        AdminEmail = adminEmail;
    }

    public String getLabName() {
        return LabName;
    }

    public void setLabName(String labName) {
        LabName = labName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public ServiceModel(String adminEmail, String labName, String phone, String otp) {
        AdminEmail = adminEmail;
        LabName = labName;
        Phone = phone;
        this.otp = otp;
    }
    public  ServiceModel(){

    }
}
