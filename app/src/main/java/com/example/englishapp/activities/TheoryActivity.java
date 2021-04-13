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
import com.example.englishapp.R;
import com.example.englishapp.Section;
import com.example.englishapp.Word;

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


//                            TextView ruWord = new TextView(getContext());
//                            ruWord.setText(w.getRuWord());
//                            ruWord.setLayoutParams(params);
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
                            //row1.addView(ruWord);
                            row1.addView(button);
                            TableRow.LayoutParams rowParams = new TableRow.LayoutParams();
                            rowParams.setMargins(0, 20,0,20);
                            row1.setLayoutParams(rowParams);
                            table.addView(row1);

//                            TableRow row2 = new TableRow(getContext());
//                            TextView definition = new TextView(getContext());
//                            definition.setText(w.getDefinition());
//                            TableRow.LayoutParams params2 = new TableRow.LayoutParams
//                                    (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                            params2.span = 4;
//                            definition.setLayoutParams(params2);
//                            row2.addView(definition);
//                            table.addView(row2);
//
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
        Intent intent = new Intent(this, WordCardActivity.class);
        intent.putExtra("word_id", view.getId());
        startActivity(intent);
//        int wordId = view.getId();
//        ServerRequest request = new ServerRequest(1, wordId);
//        APIWorker.getInstance()
//                .getJSONApi()
//                .addToDictionary(request)
//                .enqueue(new Callback<ServerResponse>() {
//                    @Override
//                    public void onResponse(@NonNull Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {
//                        ServerResponse word = response.body();
//                        Toast toast = Toast.makeText(getContext(), "'" + word.getMessage() +"' added to your dictionary",Toast.LENGTH_LONG);
//                        toast.show();
//                    }
//
//                    @Override
//                    public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {
//                        t.printStackTrace();
//                    }
//                });
    }
}