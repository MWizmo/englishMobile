package com.example.englishapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.englishapp.APIWorker;
import com.example.englishapp.R;
import com.example.englishapp.Word;
import com.example.englishapp.WordStat;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserStatsActivity extends BaseActivity {

    @Override
    public void setView() {
        setContentView(R.layout.activity_user_stats);
        TableLayout table = (TableLayout)findViewById(R.id.statTable);
        APIWorker.getInstance()
                .getJSONApi()
                .getUserStats(getAuthedUserId())
                .enqueue(new Callback<List<WordStat>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<WordStat>> call, @NonNull Response<List<WordStat>> response) {
                        List<WordStat> stats = response.body();
                        if(stats.size() == 0)
                            findViewById(R.id.noStatText).setVisibility(View.VISIBLE);
                        for (WordStat ws: stats) {
                            TableRow row = new TableRow(getContext());
                            TextView enWord = new TextView(getContext());
                            enWord.setText(ws.getEnWord());
                            TableRow.LayoutParams params = new TableRow.LayoutParams
                                    (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            params.weight = 50;
                            enWord.setLayoutParams(params);
                            enWord.setTextColor(Color.rgb(0, 0, 0));
                            enWord.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                            enWord.setTextSize(18);

                            TextView rate = new TextView(getContext());
                            float percents = (float)ws.getCorrectAttempts() / (float)(ws.getWrongAttempts() + ws.getCorrectAttempts());
                            percents *= 100;
                            String formattedPercents = String.format("%.1f", percents) + " %";
                            rate.setText(formattedPercents);
                            rate.setTextSize(18);
                            rate.setLayoutParams(params);
                            row.addView(enWord);
                            row.addView(rate);
                            table.addView(row);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<WordStat>> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    @Override
    public void openNewActivity(View view) throws IOException {

    }
}