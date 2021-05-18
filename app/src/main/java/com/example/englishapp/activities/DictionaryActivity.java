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
import android.widget.Toast;

import com.example.englishapp.APIWorker;
import com.example.englishapp.Collocation;
import com.example.englishapp.Constants;
import com.example.englishapp.R;
import com.example.englishapp.Word;
import com.google.android.material.tabs.TabLayout;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DictionaryActivity extends BaseActivity {

    private boolean areWords = true;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void setView() {
        setContentView(R.layout.activity_dictionary);
        TabLayout tabs = findViewById(R.id.tabLayout);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getText().toString().equals("Words"))
                    loadUserWords(new View(getContext()));
                else
                    loadUserCollocations(new View(getContext()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        loadUserWords(new View(this));
    }

    @Override
    public void openNewActivity(View view) {
        Intent intent = new Intent(this, WordCardActivity.class);
        intent.putExtra("word_id", view.getId());
        intent.putExtra("isWord", areWords);
        startActivity(intent);
    }

    public void loadUserWords(View view){
        areWords = true;
        findViewById(R.id.dictionaryEmptyText).setVisibility(View.GONE);
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.wordsList);
        LinearLayout collLayout = (LinearLayout)findViewById(R.id.collocationsList);
        collLayout.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);
        APIWorker.getInstance()
                .getJSONApi()
                .getWordsOfUser(getAuthedUserId())
                .enqueue(new Callback<List<Word>>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onResponse(@NonNull Call<List<Word>> call, @NonNull Response<List<Word>> response) {
                        List<Word> words = response.body();
                        linearLayout.removeAllViews();
                        if(words.size() == 0)
                            findViewById(R.id.dictionaryEmptyText).setVisibility(View.VISIBLE);
                        else{
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
                            if(words.size() > 3){
                                Button button = new Button(getContext());
                                button.setText("Train");
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                                        (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                layoutParams.setMargins(20, 10, 20, 30);
                                button.setLayoutParams(layoutParams);
                                button.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                button.setTextSize(15);
                                button.setBackgroundColor(Color.rgb(255, 218, 185));
                                button.setTextColor(Color.BLACK);
                                button.setOnClickListener((DictionaryActivity.this::goToTrain));
                                linearLayout.addView(button);
                            }
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Word>> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public void loadUserCollocations(View view){
        areWords = false;
        findViewById(R.id.dictionaryEmptyText).setVisibility(View.GONE);
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.collocationsList);
        LinearLayout wordLayout = (LinearLayout)findViewById(R.id.wordsList);
        wordLayout.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);
        APIWorker.getInstance()
                .getJSONApi()
                .getCollocationsOfUser(getAuthedUserId())
                .enqueue(new Callback<List<Collocation>>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onResponse(@NonNull Call<List<Collocation>> call, @NonNull Response<List<Collocation>> response) {
                        linearLayout.removeAllViews();
                        List<Collocation> words = response.body();
                        if(words.size() == 0)
                            findViewById(R.id.dictionaryEmptyText).setVisibility(View.VISIBLE);
                        else{
                            for (Collocation word: words) {
                                Button button = new Button(getContext());
                                button.setId(word.getId());
                                button.setText(word.getFull());
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
                            if(words.size() > 3){
                                Button button = new Button(getContext());
                                button.setText("Train");
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                                        (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                layoutParams.setMargins(20, 10, 20, 30);
                                button.setLayoutParams(layoutParams);
                                button.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                button.setTextSize(15);
                                button.setBackgroundColor(Color.rgb(255, 218, 185));
                                button.setTextColor(Color.BLACK);
                                button.setOnClickListener((DictionaryActivity.this::goToTrain));
                                linearLayout.addView(button);
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Collocation>> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void goToTrain(View view){
        int[] choices;
        if(areWords){
             choices = new int[] {2, 3, 4};
        }
        else{
            choices = new int[] {9, 10, 11};
        }
        int randomNum = ThreadLocalRandom.current().nextInt(0, choices.length);
        int randomTaskType = choices[randomNum];
        Intent intent = new Intent(getContext(), DictionaryTaskActivity.class);
        intent.putExtra("taskType", randomTaskType);
        startActivity(intent);
    }

}