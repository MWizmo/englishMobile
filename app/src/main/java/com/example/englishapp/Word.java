package com.example.englishapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Word {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("enWord")
    @Expose
    private String enWord;
    @SerializedName("ruWord")
    @Expose
    private String ruWord;
    @SerializedName("partOfSpeech")
    @Expose
    private String partOfSpeech;
    @SerializedName("definition")
    @Expose
    private String definition;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("inDictionary")
    @Expose
    private boolean inDictionary;
    @SerializedName("sectionId")
    @Expose
    private int sectionId;
    @SerializedName("audio")
    @Expose
    private boolean audio;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public boolean isInDictionary() {
        return inDictionary;
    }

    public void setInDictionary(boolean inDictionary) {
        this.inDictionary = inDictionary;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public boolean isAudio() {
        return audio;
    }

    public void setAudio(boolean audio) {
        this.audio = audio;
    }
}
