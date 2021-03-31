package com.example.englishapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.englishapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showDictionary(View view) {
        Intent intent = new Intent(this, DictionaryActivity.class);
        startActivity(intent);
    }

    public void toModules(View view) {
        Intent intent = new Intent(this, ModulesActivity.class);
        startActivity(intent);
    }
}