package com.example.administrator.layout;

import android.widget.ImageButton;

public class Item  {
    int image1;
    int image2;
    String name;
    String company;
   int imageButton;

    public int getImageButton() {
        return imageButton;
    }

    public void setImageButton(int imageButton) {
        this.imageButton = imageButton;
    }

    public int getImage1() {
        return image1;
    }

    public void setImage1(int image1) {
        this.image1 = image1;
    }

    public int getImage2() {
        return image2;
    }

    public void setImage2(int image2) {
        this.image2 = image2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Item(int image1, int image2, String name, String company) {
        this.image1 = image1;
        this.image2 = image2;
        this.name = name;
        this.company = company;

    }
}
