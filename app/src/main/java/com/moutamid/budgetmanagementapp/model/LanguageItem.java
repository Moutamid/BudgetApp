package com.moutamid.budgetmanagementapp.model;

public class LanguageItem {
    private final String languageName;
    private final int flagImage;

    public LanguageItem(String languageName, int flagImage) {
        this.languageName = languageName;
        this.flagImage = flagImage;
    }

    public String getLanguageName() {
        return languageName;
    }

    public int getFlagImage() {
        return flagImage;
    }
}
