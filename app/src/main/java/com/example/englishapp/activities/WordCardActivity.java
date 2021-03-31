package com.example.englishapp.activities;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.englishapp.APIWorker;
import com.example.englishapp.R;
import com.example.englishapp.Word;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WordCardActivity extends BaseActivity {
    @Override
    public void setView() {
        setContentView(R.layout.activity_word_card);
        Intent intent = getIntent();
        int word_id = intent.getIntExtra("word_id", 0);
        APIWorker.getInstance()
                .getJSONApi()
                .getWordById(word_id)
                .enqueue(new Callback<Word>() {
                    @Override
                    public void onResponse(@NonNull Call<Word> call, @NonNull Response<Word> response) {
                        Word current_word = response.body();
                        TextView t=(TextView)findViewById(R.id.textView2);
                        t.setText(current_word.getEnWord());
                        t=(TextView)findViewById(R.id.textView3);
                        t.setText(current_word.getRuWord());
                        t=(TextView)findViewById(R.id.textView4);
                        t.setText(String.format("(%s) %s", current_word.getPartOfSpeech(), current_word.getDefinition()));
                    }

                    @Override
                    public void onFailure(@NonNull Call<Word> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    @Override
    public void openNewActivity(View view) {

    }

}