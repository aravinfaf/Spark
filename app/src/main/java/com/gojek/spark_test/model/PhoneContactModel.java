package com.gojek.spark_test.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PhoneContactModel {

    @PrimaryKey(autoGenerate = true)
    int id;
    String name;
    String phone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
