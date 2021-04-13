package com.example.englishapp.requests;

public class UserStatServerRequest {
    public int userId;
    public int wordId;
    public int result;

    public UserStatServerRequest(int userId, int wordId, int result){
        this.wordId = wordId;
        this.userId = userId;
        this.result = result;
    }
}
