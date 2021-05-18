package com.example.englishapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.englishapp.APIWorker;
import com.example.englishapp.Collocation;
import com.example.englishapp.R;
import com.example.englishapp.Section;
import com.example.englishapp.ServerResponse;
import com.example.englishapp.Word;
import com.example.englishapp.requests.UserStatServerRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TheoryActivity extends BaseActivity {

    private int theoryType;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void setView() {
        setContentView(R.layout.activity_theory);
        Intent intent = getIntent();
        int sectionId = intent.getIntExtra("sectionId", 0);
        int taskId = intent.getIntExtra("taskId", 0);
        theoryType = intent.getIntExtra("theoryType", 1);
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
        if(theoryType == 1)
            APIWorker.getInstance()
                .getJSONApi()
                .getSectionWords(sectionId)
                .enqueue(new Callback<List<Word>>() {
                    @SuppressLint("ResourceAsColor")
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
                            params.weight = 50;
                            enWord.setLayoutParams(params);
                            enWord.setTextColor(Color.rgb(0, 0, 0));
                            enWord.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                            enWord.setTextSize(24);

                            TextView pOS = new TextView(getContext());
                            pOS.setTextSize(20);
                            pOS.setText(w.getPartOfSpeech());
                            params.weight = 40;
                            pOS.setLayoutParams(params);

                            Button button = new Button(getContext());
                            int widthInDp = 20;
                            int widthInPx = (int) TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_DIP, widthInDp, getResources().getDisplayMetrics());
                            int heightInDp = 30;
                            int heightInPx = (int) TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_DIP, heightInDp, getResources().getDisplayMetrics());
                            TableRow.LayoutParams button_params = new TableRow.LayoutParams(widthInPx,heightInPx);
                            button_params.weight = 10;
                            button.setLayoutParams(button_params);
                            button.setBackgroundResource(R.drawable.lup_icon);
                            button.setId(w.getId());
                            button.setOnClickListener((TheoryActivity.this::openNewActivity));
                            row1.addView(enWord);
                            row1.addView(pOS);
                            row1.addView(button);
                            TableRow.LayoutParams rowParams = new TableRow.LayoutParams();
                            rowParams.setMargins(0, 20,0,20);
                            row1.setLayoutParams(rowParams);
                            table.addView(row1);

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
            else
                APIWorker.getInstance()
                    .getJSONApi()
                    .getSectionCollocations(sectionId)
                    .enqueue(new Callback<List<Collocation>>() {
                        @SuppressLint("ResourceAsColor")
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                        @Override
                        public void onResponse(@NonNull Call<List<Collocation>> call, @NonNull Response<List<Collocation>> response) {
                            List<Collocation> collocations = response.body();
                            for (Collocation c: collocations) {
                                TableRow row1 = new TableRow(getContext());
                                TextView enWord = new TextView(getContext());
                                enWord.setText(c.getFull());
                                TableRow.LayoutParams params = new TableRow.LayoutParams
                                        (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                params.weight = 50;
                                enWord.setLayoutParams(params);
                                enWord.setTextColor(Color.rgb(0, 0, 0));
                                enWord.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                                enWord.setTextSize(24);

                                Button button = new Button(getContext());
                                int widthInDp = 20;
                                int widthInPx = (int) TypedValue.applyDimension(
                                        TypedValue.COMPLEX_UNIT_DIP, widthInDp, getResources().getDisplayMetrics());
                                int heightInDp = 30;
                                int heightInPx = (int) TypedValue.applyDimension(
                                        TypedValue.COMPLEX_UNIT_DIP, heightInDp, getResources().getDisplayMetrics());
                                TableRow.LayoutParams button_params = new TableRow.LayoutParams(widthInPx,heightInPx);
                                button_params.weight = 10;
                                button.setLayoutParams(button_params);
                                button.setBackgroundResource(R.drawable.lup_icon);
                                button.setId(c.getId());
                                button.setOnClickListener((TheoryActivity.this::openNewActivity));
                                row1.addView(enWord);
                                row1.addView(button);
                                TableRow.LayoutParams rowParams = new TableRow.LayoutParams();
                                rowParams.setMargins(0, 20,0,20);
                                row1.setLayoutParams(rowParams);
                                table.addView(row1);

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
                        public void onFailure(@NonNull Call<List<Collocation>> call, @NonNull Throwable t) {
                            t.printStackTrace();
                        }
                    });

        UserStatServerRequest request = new UserStatServerRequest(getAuthedUserId(), -1, 1, taskId);
        APIWorker.getInstance().getJSONApi().fix_stat(request)
                .enqueue(new Callback<ServerResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {
                    }

                    @Override
                    public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    @Override
    public void openNewActivity(View view) {
        Intent intent = new Intent(this, WordCardActivity.class);
        intent.putExtra("word_id", view.getId());
        intent.putExtra("isWord", theoryType == 1);
        startActivity(intent);
    }
}