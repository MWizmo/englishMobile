package com.example.englishapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.englishapp.APIWorker;
import com.example.englishapp.R;
import com.example.englishapp.Section;
import com.example.englishapp.ServerRequest;
import com.example.englishapp.ServerResponse;
import com.example.englishapp.Task;
import com.example.englishapp.Word;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TheoryActivity extends BaseActivity {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void setView() {
        setContentView(R.layout.activity_theory);
        Intent intent = getIntent();
        int sectionId = intent.getIntExtra("sectionId", 0);
        APIWorker.getInstance()
                .getJSONApi()
                .getSectionInfo(sectionId)
                .enqueue(new Callback<Section>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onResponse(@NonNull Call<Section> call, @NonNull Response<Section> response) {
                        Section s = response.body();
                        TextView title = (TextView) findViewById(R.id.textView5);
                        String text = title.getText() + "'" + s.getTitle() + "' section";
                        title.setText(text);
                    }
                    @Override
                    public void onFailure(@NonNull Call<Section> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
        TableLayout table = (TableLayout)findViewById(R.id.theoryTable);
        APIWorker.getInstance()
                .getJSONApi()
                .getSectionWords(sectionId)
                .enqueue(new Callback<List<Word>>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onResponse(@NonNull Call<List<Word>> call, @NonNull Response<List<Word>> response) {
                        List<Word> words = response.body();
                        for (Word w: words) {
                            TableRow row1 = new TableRow(getContext());
                            TextView enWord = new TextView(getContext());
                            enWord.setText(w.getEnWord());
                            TableRow.LayoutParams params = new TableRow.LayoutParams
                                    (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            params.weight = 30;
                            enWord.setLayoutParams(params);

                            TextView ruWord = new TextView(getContext());
                            ruWord.setText(w.getRuWord());
                            ruWord.setLayoutParams(params);
                            TextView pOS = new TextView(getContext());
                            pOS.setText(w.getPartOfSpeech());
                            pOS.setLayoutParams(params);

                            Button button = new Button(getContext());
                            params.weight = 10;
                            button.setLayoutParams(params);
                            button.setText("+");
                            button.setId(w.getId());
                            button.setOnClickListener((TheoryActivity.this::openNewActivity));
                            row1.addView(enWord);
                            row1.addView(pOS);
                            row1.addView(ruWord);
                            row1.addView(button);
                            table.addView(row1);

                            TableRow row2 = new TableRow(getContext());
                            TextView definition = new TextView(getContext());
                            definition.setText(w.getDefinition());
                            TableRow.LayoutParams params2 = new TableRow.LayoutParams
                                    (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            params2.span = 4;
                            definition.setLayoutParams(params2);
                            row2.addView(definition);
                            table.addView(row2);

                            View border = new View(getContext());
                            TableRow.LayoutParams borderParams = new TableRow.LayoutParams
                                    (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            borderParams.height=2;
                            border.setBackgroundColor(Color.rgb(0, 0, 139));
                            border.setLayoutParams(borderParams);
                            table.addView(border);
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
        int wordId = view.getId();
        ServerRequest request = new ServerRequest(1, wordId);
        APIWorker.getInstance()
                .getJSONApi()
                .addToDictionary(request)
                .enqueue(new Callback<ServerResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {
                        ServerResponse word = response.body();
                        Toast toast = Toast.makeText(getContext(), "'" + word.getMessage() +"' added to your dictionary",Toast.LENGTH_LONG);
                        toast.show();
                    }

                    @Override
                    public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }
}