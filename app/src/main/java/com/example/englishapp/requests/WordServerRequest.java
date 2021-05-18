package com.example.englishapp.requests;

public class WordServerRequest {
    public int userId;
    public int wordId;
    public boolean isWord;

    public WordServerRequest(int userId, int wordId, boolean isWord){
        this.wordId = wordId;
        this.userId = userId;
        this.isWord = isWord;
    }
}
