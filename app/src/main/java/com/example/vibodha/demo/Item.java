package com.example.vibodha.demo;

import android.graphics.Bitmap;

/**
 * Created by vibodha on 11/10/16.
 */
public class Item {
    private Bitmap image;
    private String treatmentName;
    private String date;
    private String Status;

    public Item(Bitmap image, String treatmentName, String date, String status) {
        this.image = image;
        this.treatmentName = treatmentName;
        this.date = date;
        Status = status;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
