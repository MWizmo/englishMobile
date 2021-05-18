package com.example.englishapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TranslationTask {
    @SerializedName("enWord")
    @Expose
    private String enWord;
    @SerializedName("ruWord")
    @Expose
    private String ruWord;
    @SerializedName("definition")
    @Expose
    private String definition;
    @SerializedName("isRight")
    @Expose
    private boolean isRight;
    @SerializedName("wordId")
    @Expose
    private int wordId;

    public String getEnWord() {
        return enWord;
    }

    public void setEnWord(String enWord) {
        this.enWord = enWord;
    }

    public String getRuWord() {
        return ruWord;
    }

    public void setRuWord(String ruWord) {
        this.ruWord = ruWord;
    }

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean right) {
        isRight = right;
    }

    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
