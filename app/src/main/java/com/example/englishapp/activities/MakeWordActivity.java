package com.example.englishapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.englishapp.APIWorker;
import com.example.englishapp.R;
import com.example.englishapp.SentenceTask;
import com.example.englishapp.TranslationTask;
import com.google.android.flexbox.FlexboxLayout;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakeWordActivity extends BaseActivity {

    private int rightWordId;
    private int taskId;

    @Override
    public void setView() {
        setContentView(R.layout.activity_make_word);
        Intent intent = getIntent();
        int sectionId = intent.getIntExtra("sectionId", 0);
        taskId = intent.getIntExtra("taskId", 0);
        int previousSentenceId = intent.getIntExtra("previousWordId", 0);
        FlexboxLayout layout = findViewById(R.id.letters);
        APIWorker.getInstance()
                .getJSONApi()
                .getMakeWordTask(sectionId, previousSentenceId)
                .enqueue(new Callback<TranslationTask>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @SuppressLint("ResourceType")
                    @Override
                    public void onResponse(@NonNull Call<TranslationTask> call, @NonNull Response<TranslationTask> response) {
                        TranslationTask task = response.body();
                        TextView wordText = (TextView) findViewById(R.id.definition);
                        wordText.setText(task.getDefinition());
                        StringBuilder blanks = new StringBuilder();
                        for(int i=0; i< task.getEnWord().length(); i++){
                            Button button = new Button(getContext());
                            button.setId(i);
                            button.setText("" + task.getEnWord().charAt(i));
                            button.setBackgroundColor(Color.rgb(135, 206, 250));
                            FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams
                                    (FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
                            //layoutParams.setMargins(20, 10, 20, 30);
                            layoutParams.rightMargin = 30;
                            layoutParams.bottomMargin = 30;
                            button.setLayoutParams(layoutParams);
                            button.setTextSize(15);
                            button.setTextColor(Color.BLACK);
                            button.setOnClickListener((MakeWordActivity.this::openNewActivity));
                            layout.addView(button);
                            blanks.append("_ ");
                        }
                        TextView newWordText = (TextView) findViewById(R.id.newWord);
                        newWordText.setText(blanks);
                    }
                    @Override
                    public void onFailure(@NonNull Call<TranslationTask> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    @Override
    public void openNewActivity(View view){
        Button button = (Button) view;
        TextView newWordText = (TextView) findViewById(R.id.newWord);
        String text = (String) newWordText.getText();
        text = text.replaceFirst("_ ", (String) button.getText());
        newWordText.setText(text);
        view.setClickable(false);
        view.setBackgroundColor(Color.rgb(176, 196, 222));
    }
}