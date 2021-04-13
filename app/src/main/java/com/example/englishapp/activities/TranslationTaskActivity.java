package com.example.englishapp.activities;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.englishapp.APIWorker;
import com.example.englishapp.R;
import com.example.englishapp.ServerResponse;
import com.example.englishapp.TranslationTask;
import com.example.englishapp.requests.UserStatServerRequest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TranslationTaskActivity extends BaseActivity {

    private int rightWordId;
    private int taskId;

    @Override
    public void setView() {
        setContentView(R.layout.activity_translation_task);
        Intent intent = getIntent();
        int sectionId = intent.getIntExtra("sectionId", 0);
        int taskType = intent.getIntExtra("taskType", 0);
        taskId = intent.getIntExtra("taskId", 0);
        TextView title = (TextView) findViewById(R.id.taskTitle);
        int previousWordId = intent.getIntExtra("previousWordId", 0);

        if (taskType == 2){
            String text = title.getText() + "English to Russian";
            title.setText(text);
        }
        else{
            String text = title.getText() + "Russian to English";
            title.setText(text);
        }
        APIWorker.getInstance()
                .getJSONApi()
                .getTranslationTask(sectionId, previousWordId)
                .enqueue(new Callback<List<TranslationTask>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<TranslationTask>> call, @NonNull Response<List<TranslationTask>> response) {
                        List<TranslationTask> items = response.body();
                        for (TranslationTask t: items) {
                            if(t.isRight()){
                                TextView wordText = (TextView) findViewById(R.id.wordToTranslate);
                                if (taskType == 2){
                                    wordText.setText(t.getEnWord());
                                }
                                else{
                                    wordText.setText(t.getRuWord());
                                }
                                rightWordId = t.getWordId();
                                break;
                            }
                        }
                        ArrayList<Button> buttons = new ArrayList<Button>();
                        buttons.add(findViewById(R.id.buttonAnswer1));
                        buttons.add(findViewById(R.id.buttonAnswer2));
                        buttons.add(findViewById(R.id.buttonAnswer3));
                        buttons.add(findViewById(R.id.buttonAnswer4));
                        for(int i = 0; i < 4; i++){
                            if (taskType == 2){
                                buttons.get(i).setText(items.get(i).getRuWord());
                            }
                            else{
                                buttons.get(i).setText(items.get(i).getEnWord());
                            }
                            buttons.get(i).setId(items.get(i).getWordId());
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<List<TranslationTask>> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
        });
    }

    @Override
    public void openNewActivity(View view){
        int result;
        if(view.getId() == rightWordId){
            Toast toast = Toast.makeText(getContext(), "Right !!!", Toast.LENGTH_SHORT);
            toast.show();
            result = 1;
            findViewById(R.id.newWordButton).setVisibility(View.VISIBLE);
        }
        else{
            Toast toast = Toast.makeText(getContext(), "Wrong (((", Toast.LENGTH_SHORT);
            toast.show();
            result = 0;
            findViewById(R.id.newWordButton).setVisibility(View.INVISIBLE);
        }
        UserStatServerRequest request = new UserStatServerRequest(1, rightWordId, result);
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

    public void newWord(View view){
        Intent curIntent = getIntent();
        Intent newIntent = new Intent(getContext(), TranslationTaskActivity.class);
        newIntent.putExtra("sectionId", curIntent.getIntExtra("sectionId", 0));
        newIntent.putExtra("taskId", taskId);
        newIntent.putExtra("taskType", curIntent.getIntExtra("taskType", 0));
        newIntent.putExtra("previousWordId", rightWordId);
        finish();
        startActivity(newIntent);
    }
}