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
import com.example.englishapp.R;
import com.example.englishapp.Section;
import com.example.englishapp.Task;
import com.example.englishapp.TranslationTask;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SectionActivity extends BaseActivity {
    @Override
    public void setView() {
        Intent intent = getIntent();
        int section_id = intent.getIntExtra("section_id", 0);
        setContentView(R.layout.activity_section);
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.taskInSectionList);
        APIWorker.getInstance()
                .getJSONApi()
                .getSectionInfo(section_id)
                .enqueue(new Callback<Section>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onResponse(@NonNull Call<Section> call, @NonNull Response<Section> response) {
                        Section s = response.body();
                        TextView title = findViewById(R.id.sectionTitleText1);
                        title.setText("Section: " + s.getTitle());
                    }
                    @Override
                    public void onFailure(@NonNull Call<Section> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
        APIWorker.getInstance()
                .getJSONApi()
                .getTasksFromSection(section_id)
                .enqueue(new Callback<List<Task>>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onResponse(@NonNull Call<List<Task>> call, @NonNull Response<List<Task>> response) {
                        List<Task> tasks = response.body();
                        for (Task t: tasks) {
                            Button button = new Button(getContext());
                            button.setId(t.getId());
                            switch (t.getType()) {
                                case 1:
                                    button.setText("Theory");
                                    button.setBackgroundColor(Color.rgb(135, 206, 250));
                                    break;
                                case 2:
                                    button.setText("Task: translate to Russian");
                                    button.setBackgroundColor(Color.rgb(135, 206, 250));
                                    break;
                                case 3:
                                    button.setText("Task: translate to English");
                                    button.setBackgroundColor(Color.rgb(135, 206, 250));
                            }
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                                    (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            layoutParams.setMargins(20, 10, 20, 30);
                            button.setLayoutParams(layoutParams);
                            button.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                            button.setTextSize(15);
                            button.setTextColor(Color.BLACK);
                            button.setOnClickListener((SectionActivity.this::openNewActivity));
                            linearLayout.addView(button);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Task>> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    @Override
    public void openNewActivity(View view) {
        int taskId = view.getId();
        APIWorker.getInstance()
                .getJSONApi()
                .getTaskInfo(taskId)
                .enqueue(new Callback<Task>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onResponse(@NonNull Call<Task> call, @NonNull Response<Task> response) {
                        Task t = response.body();
                        switch (t.getType()){
                            case 1:
                                Intent intent = new Intent(getContext(), TheoryActivity.class);
                                intent.putExtra("sectionId", t.getSectionId());
                                startActivity(intent);
                                break;
                            case 2:
                                intent = new Intent(getContext(), TranslationTaskActivity.class);
                                intent.putExtra("sectionId", t.getSectionId());
                                intent.putExtra("taskId", t.getId());
                                intent.putExtra("taskType", 2);
                                startActivity(intent);
                                break;
                            case 3:
                                intent = new Intent(getContext(), TranslationTaskActivity.class);
                                intent.putExtra("sectionId", t.getSectionId());
                                intent.putExtra("taskType", 3);
                                intent.putExtra("taskId", t.getId());
                                startActivity(intent);
                                break;
                            default:
                                break;
                        }

                    }
                    @Override
                    public void onFailure(@NonNull Call<Task> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }
}