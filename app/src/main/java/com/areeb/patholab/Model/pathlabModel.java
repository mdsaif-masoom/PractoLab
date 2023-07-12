package com.areeb.patholab.Model;

public class pathlabModel {
    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getPhone() {
        return Phone;
    }


    public pathlabModel(){}

    public pathlabModel(String fullname, String phone, String adminemail, String id, String profilePic) {
        Fullname = fullname;
        Phone = phone;
        AdminEmail = adminemail;
        Id = id;
        ProfilePic= profilePic;
    }

    public void setProfilePic(String profilePic){
        ProfilePic = profilePic;
    }

    public String getProfilePic(){
        return ProfilePic;
    }
    public void setPhone(String phone) {
        Phone = phone;
    }
    public String getId(){
        return Id;
    }

    public void setId(String id){
        Id = id;
    }

    public String getAdminEmail() {
        return AdminEmail;
    }

    public void setAdminEmail(String userEmail) {
        AdminEmail = userEmail;
    }

    String Fullname,Phone,AdminEmail, Id,ProfilePic;


}
