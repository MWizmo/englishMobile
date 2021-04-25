package com.example.englishapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WordStat {
    @SerializedName("enWord")
    @Expose
    private String enWord;
    @SerializedName("correctAttempts")
    @Expose
    private int correctAttempts;
    @SerializedName("wrongAttempts")
    @Expose
    private int wrongAttempts;

    public String getEnWord() {
        return enWord;
    }

    public void setEnWord(String enWord) {
        this.enWord = enWord;
    }

    public int getCorrectAttempts() {
        return correctAttempts;
    }

    public void setCorrectAttempts(int correctAttempts) {
        this.correctAttempts = correctAttempts;
    }

    public int getWrongAttempts() {
        return wrongAttempts;
    }

    public void setWrongAttempts(int wrongAttempts) {
        this.wrongAttempts = wrongAttempts;
    }
}
