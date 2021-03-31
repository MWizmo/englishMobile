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
import com.example.englishapp.Module;
import com.example.englishapp.Section;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SectionsListActivity extends BaseActivity {
    @Override
    public void setView() {
        Intent intent = getIntent();
        int module_id = intent.getIntExtra("module_id", 0);
        ScrollView scrollView = new ScrollView(this);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        APIWorker.getInstance()
                .getJSONApi()
                .getModuleInfo(module_id)
                .enqueue(new Callback<Module>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onResponse(@NonNull Call<Module> call, @NonNull Response<Module> response) {
                        Module module = response.body();
                        TextView title = new TextView(getContext());
                        title.setText("Module: " + module.getTitle());
                        title.setTextSize(20);
                        LinearLayout.LayoutParams titleLayoutParams = new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        titleLayoutParams.setMargins(0, 20, 0, 50);
                        title.setLayoutParams(titleLayoutParams);
                        title.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        linearLayout.addView(title);
                    }
                    @Override
                    public void onFailure(@NonNull Call<Module> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
        APIWorker.getInstance()
                .getJSONApi()
                .getSections(module_id)
                .enqueue(new Callback<List<Section>>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onResponse(@NonNull Call<List<Section>> call, @NonNull Response<List<Section>> response) {
                        List<Section> sections = response.body();
                        for (Section s: sections) {
                            Button button = new Button(getContext());
                            button.setId(s.getId());
                            button.setText(s.getTitle());
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                                    (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            layoutParams.setMargins(20, 10, 20, 30);
                            button.setLayoutParams(layoutParams);
                            button.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                            button.setTextSize(15);
                            button.setBackgroundColor(Color.rgb(240, 248, 255));
                            button.setTextColor(Color.BLACK);
                            button.setOnClickListener((SectionsListActivity.this::openNewActivity));
                            linearLayout.addView(button);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Section>> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
        scrollView.addView(linearLayout);
        setContentView(scrollView);
    }

    @Override
    public void openNewActivity(View view) {
        Intent intent = new Intent(this, SectionActivity.class);
        intent.putExtra("section_id", view.getId());
        startActivity(intent);
    }
}