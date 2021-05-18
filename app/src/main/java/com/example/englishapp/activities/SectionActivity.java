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
                .getTasksFromSection(section_id, getAuthedUserId())
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
                                    button.setText("WORDS: Theory");
                                    break;
                                case 2:
                                    button.setText("WORDS: Translate to Russian");
                                    break;
                                case 3:
                                    button.setText("WORDS: Translate to English");
                                    break;
                                case 4:
                                    button.setText("WORDS: Choose by definition");
                                    break;
                                case 5:
                                    button.setText("WORDS: Make a word from letters");
                                    break;
                                case 6:
                                    button.setText("WORDS: Choose by audio");
                                    break;
                                case 7:
                                    button.setText("WORDS: Translate to Russian by audio");
                                    break;
                                case 8:
                                    button.setText("COLLOCATIONS: Theory");
                                    break;
                                case 9:
                                    button.setText("COLLOCATIONS: Translate to Russian");
                                    break;
                                case 10:
                                    button.setText("COLLOCATIONS: Translate to English");
                                    break;
                                case 11:
                                    button.setText("COLLOCATIONS: Match beginning and end");
                                    break;
                                case 12:
                                    button.setText("SENTENSES: Insert missing word");
                                    break;
                            }
//                            if(t.getType() == 1 || t.getType() == 8)
//                                button.setBackgroundColor(Color.rgb(255, 218, 185));
//                            else{
//                                if(t.isCompleted()){
//                                    button.setText(button.getText() + "  [completed]");
//                                    button.setBackgroundColor(Color.rgb(152, 251, 152));
//                                }
//                                else{
//                                    button.setText(button.getText() + "  [not completed]");
//                                    button.setBackgroundColor(Color.rgb(135, 206, 250));
//                                }
//                            }
                            if(t.isCompleted()){
                                    button.setText(button.getText() + "  [completed]");
                                    button.setBackgroundColor(Color.rgb(152, 251, 152));
                                }
                                else{
                                    button.setText(button.getText() + "  [not completed]");
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
                        Intent intent;
                        switch (t.getType()){
                            case 1:
                                intent = new Intent(getContext(), TheoryActivity.class);
                                intent.putExtra("sectionId", t.getSectionId());
                                intent.putExtra("theoryType", 1);
                                intent.putExtra("taskId", t.getId());
                                startActivity(intent);
                                break;
                            case 8:
                                intent = new Intent(getContext(), TheoryActivity.class);
                                intent.putExtra("sectionId", t.getSectionId());
                                intent.putExtra("theoryType", 2);
                                intent.putExtra("taskId", t.getId());
                                startActivity(intent);
                                break;
                            case 2:
                            case 3:
                            case 4:
                            case 9:
                            case 10:
                            case 11:
                                intent = new Intent(getContext(), TranslationTaskActivity.class);
                                intent.putExtra("sectionId", t.getSectionId());
                                intent.putExtra("taskId", t.getId());
                                intent.putExtra("taskType", t.getType());
                                startActivity(intent);
                                break;
                            case 12:
                                intent = new Intent(getContext(), SentenceTaskActivity.class);
                                intent.putExtra("sectionId", t.getSectionId());
                                intent.putExtra("taskId", t.getId());
                                startActivity(intent);
                                break;
                            case 6:
                            case 7:
                                intent = new Intent(getContext(), AudioTaskActivity.class);
                                intent.putExtra("sectionId", t.getSectionId());
                                intent.putExtra("taskId", t.getId());
                                intent.putExtra("taskType", t.getType());
                                startActivity(intent);
                                break;
                            case 5:
                                intent = new Intent(getContext(), MakeWordActivity.class);
                                intent.putExtra("sectionId", t.getSectionId());
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