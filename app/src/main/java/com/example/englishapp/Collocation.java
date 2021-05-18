package com.example.englishapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Collocation {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("firstPart")
    @Expose
    private String firstPart;
    @SerializedName("secondPart")
    @Expose
    private String secondPart;
    @SerializedName("ruTranslation")
    @Expose
    private String ruTranslation;
    @SerializedName("inDictionary")
    @Expose
    private boolean inDictionary;
    @SerializedName("audio")
    @Expose
    private boolean audio;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isInDictionary() {
        return inDictionary;
    }

    public void setInDictionary(boolean inDictionary) {
        this.inDictionary = inDictionary;
    }

    public String getFirstPart() {
        return firstPart;
    }

    public void setFirstPart(String firstPart) {
        this.firstPart = firstPart;
    }

    public String getSecondPart() {
        return secondPart;
    }

    public void setSecondPart(String secondPart) {
        this.secondPart = secondPart;
    }

    public String getRuTranslation() {
        return ruTranslation;
    }

    public void setRuTranslation(String ruTranslation) {
        this.ruTranslation = ruTranslation;
    }

    public String getFull(){
        return getFirstPart() + " "  + getSecondPart();
    }

    public boolean isAudio() {
        return audio;
    }

    public void setAudio(boolean audio) {
        this.audio = audio;
    }
}
