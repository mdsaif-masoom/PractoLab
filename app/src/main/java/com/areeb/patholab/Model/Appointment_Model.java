package com.areeb.patholab.Model;

public class Appointment_Model {

    String name ;
    String Phone ;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }


    public Appointment_Model(String name, String phone) {
        this.name = name;
        Phone = phone;

    }


}
