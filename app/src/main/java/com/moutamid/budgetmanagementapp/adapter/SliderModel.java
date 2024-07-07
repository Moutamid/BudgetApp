package com.moutamid.budgetmanagementapp.adapter;

public class SliderModel {
    String title;
    int progress;

    public SliderModel(String title, int progress) {
        this.title = title;
        this.progress = progress;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
