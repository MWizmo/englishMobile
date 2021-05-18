package com.example.englishapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MissingWord {
    @SerializedName("word")
    @Expose
    private String word;
    @SerializedName("wordId")
    @Expose
    private int wordId;
    @SerializedName("order")
    @Expose
    private int order;


    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
