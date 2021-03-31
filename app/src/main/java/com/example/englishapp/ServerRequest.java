package com.example.englishapp;

public class ServerRequest {
    public int userId;
    public int wordId;

    public ServerRequest(int userId, int wordId){
        this.wordId = wordId;
        this.userId = userId;
    }
}
