package com.example.englishapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.example.englishapp.APIWorker;
import com.example.englishapp.Constants;
import com.example.englishapp.R;
import com.example.englishapp.Word;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DictionaryActivity extends BaseActivity {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void setView() {
        setContentView(R.layout.activity_dictionary);
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.wordsList);
        APIWorker.getInstance()
                .getJSONApi()
                .getWordsOfUser(getAuthedUserId())
                .enqueue(new Callback<List<Word>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Word>> call, @NonNull Response<List<Word>> response) {
                        List<Word> words = response.body();
                        if(words.size() == 0)
                            findViewById(R.id.dictionaryEmptyText).setVisibility(View.VISIBLE);
                        for (Word word: words) {
                            Button button = new Button(getContext());
                            button.setId(word.getId());
                            button.setText(word.getEnWord());
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                                    (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            layoutParams.setMargins(20, 10, 20, 30);
                            button.setLayoutParams(layoutParams);
                            button.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                            button.setTextSize(15);
                            button.setBackgroundColor(Color.rgb(240, 248, 255));
                            button.setTextColor(Color.BLACK);
                            button.setOnClickListener((DictionaryActivity.this::openNewActivity));
                            linearLayout.addView(button);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Word>> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    @Override
    public void openNewActivity(View view) {
        Intent intent = new Intent(this, WordCardActivity.class);
        intent.putExtra("word_id", view.getId());
        startActivity(intent);
    }

}