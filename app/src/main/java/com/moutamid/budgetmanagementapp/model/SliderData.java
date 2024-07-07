package com.moutamid.budgetmanagementapp.model;

public class SliderData {
    private int imgUrl;
    private String text;
    public SliderData(int imgUrl, String text) {
        this.imgUrl = imgUrl;
        this.text = text;
    }
    public int getImgUrl() { return imgUrl; }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
