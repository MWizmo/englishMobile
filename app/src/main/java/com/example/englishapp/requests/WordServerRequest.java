package com.example.englishapp.requests;

public class WordServerRequest {
    public int userId;
    public int wordId;

    public WordServerRequest(int userId, int wordId){
        this.wordId = wordId;
        this.userId = userId;
    }
}
