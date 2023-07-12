package com.areeb.patholab.Model;

public class treatmentList {

    String treatmentName ;
    String treatmentPrice;

    public treatmentList(){

    }

    public String getTreatmentName() {
        return treatmentName;
    }


    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
    }

    public String getTreatmentPrice() {
        return treatmentPrice;
    }

    public void setTreatmentPrice(String treatmentPrice) {
        this.treatmentPrice = treatmentPrice;
    }

    public treatmentList(String treatmentName, String treatmentPrice) {
        this.treatmentName = treatmentName;
        this.treatmentPrice = treatmentPrice;
    }
}
