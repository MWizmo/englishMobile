package com.example.englishapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SentenceTask {
    @SerializedName("sentence")
    @Expose
    private String sentence;
    @SerializedName("words")
    @Expose
    private List<MissingWord> words;
    @SerializedName("blanks")
    @Expose
    private int blanks;
    @SerializedName("sentenceId")
    @Expose
    private int sentenceId;

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public List<MissingWord> getWords() {
        return words;
    }

    public void setWords(List<MissingWord> words) {
        this.words = words;
    }

    public int getBlanks() {
        return blanks;
    }

    public void setBlanks(int blanks) {
        this.blanks = blanks;
    }

    public int getSentenceId() {
        return sentenceId;
    }

    public void setSentenceId(int sentenceId) {
        this.sentenceId = sentenceId;
    }
}
