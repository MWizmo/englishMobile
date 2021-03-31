package com.example.englishapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Task {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("type")
    @Expose
    private int type;
    @SerializedName("taskId")
    @Expose
    private int taskId;
    @SerializedName("sectionId")
    @Expose
    private int sectionId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }
}
