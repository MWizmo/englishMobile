package com.example.englishapp.requests;

public class UserStatServerRequest {
    public int userId;
    public int wordId;
    public int result;
    public int taskId;

    public UserStatServerRequest(int userId, int wordId, int result, int taskId){
        this.wordId = wordId;
        this.userId = userId;
        this.result = result;
        this.taskId = taskId;
    }
}
