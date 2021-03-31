package com.example.englishapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.example.englishapp.APIWorker;
import com.example.englishapp.Word;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DictionaryActivity extends BaseActivity {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void setView() {
        ScrollView scrollView = new ScrollView(this);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        TextView title = new TextView(this);
        title.setText("My words");
        title.setTextSize(20);
        LinearLayout.LayoutParams titleLayoutParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        titleLayoutParams.setMargins(0, 20, 0, 50);
        title.setLayoutParams(titleLayoutParams);
        title.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        linearLayout.addView(title);
        APIWorker.getInstance()
                .getJSONApi()
                .getWordsOfUser(1)
                .enqueue(new Callback<List<Word>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Word>> call, @NonNull Response<List<Word>> response) {
                        List<Word> words = response.body();
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
        //APIWorker.getContent();

        scrollView.addView(linearLayout);
        setContentView(scrollView);
    }

    @Override
    public void openNewActivity(View view) {
        Intent intent = new Intent(this, WordCardActivity.class);
        intent.putExtra("word_id", view.getId());
        startActivity(intent);
    }

}