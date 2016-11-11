package com.example.vibodha.demo;

import android.graphics.Bitmap;

/**
 * Created by vibodha on 11/10/16.
 */
public class Item {
    private String _id;
    private String image;
    private String name;
    private String date;
    private String Status;

    public Item(String _id, String image, String name, String date, String status) {
        this._id = _id;
        this.image = image;
        this.name = name;
        this.date = date;
        Status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
