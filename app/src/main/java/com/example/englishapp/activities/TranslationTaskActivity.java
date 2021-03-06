package com.example.englishapp.activities;

import androidx.annotation.NonNull;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.englishapp.APIWorker;
import com.example.englishapp.Constants;
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
    private int rightButton;
    private int taskId;
    private boolean isWord;

    @Override
    public void setView() {
        setContentView(R.layout.activity_translation_task);
        Intent intent = getIntent();
        int sectionId = intent.getIntExtra("sectionId", 0);
        int taskType = intent.getIntExtra("taskType", 0);
        taskId = intent.getIntExtra("taskId", 0);
        TextView title = (TextView) findViewById(R.id.taskTitle);
        int previousWordId = intent.getIntExtra("previousWordId", 0);
        String text;
        switch(taskType){
            case 2:
            case 9:
                text = title.getText() + "English to Russian";
                title.setText(text);
                break;
            case 3:
            case 10:
                text = title.getText() + "Russian to English";
                title.setText(text);
                break;
            case 4:
                text = "Choose the right word by his definition";
                title.setText(text);
                break;
        }
        switch(taskType){
            case 2:
            case 3:
            case 4:
                isWord = true;
                APIWorker.getInstance()
                        .getJSONApi()
                        .getTranslationTask(sectionId, previousWordId)
                        .enqueue(new Callback<List<TranslationTask>>() {
                            @Override
                            public void onResponse(@NonNull Call<List<TranslationTask>> call, @NonNull Response<List<TranslationTask>> response) {
                                List<TranslationTask> items = response.body();
                                for (int i=0; i<items.size(); i++) {
                                    TranslationTask t = items.get(i);
                                    if (t.isRight()) {
                                        TextView wordText = (TextView) findViewById(R.id.wordToTranslate);
                                        switch (taskType) {
                                            case 2:
                                                wordText.setText(t.getEnWord());
                                                break;
                                            case 3:
                                                wordText.setText(t.getRuWord());
                                                break;
                                            case 4:
                                                wordText.setText(t.getDefinition());
                                                break;
                                        }
                                        rightWordId = t.getWordId();
                                        rightButton = i;
                                        break;
                                    }
                                }
                                ArrayList<Button> buttons = new ArrayList<Button>();
                                buttons.add(findViewById(R.id.buttonAnswer1));
                                buttons.add(findViewById(R.id.buttonAnswer2));
                                buttons.add(findViewById(R.id.buttonAnswer3));
                                buttons.add(findViewById(R.id.buttonAnswer4));
                                for (int i = 0; i < 4; i++) {
                                    switch (taskType) {
                                        case 2:
                                        case 4:
                                            buttons.get(i).setText(items.get(i).getRuWord());
                                            break;
                                        case 3:
                                            buttons.get(i).setText(items.get(i).getEnWord());
                                            break;
                                    }
                                    //buttons.get(i).setId(items.get(i).getWordId());
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<List<TranslationTask>> call, @NonNull Throwable t) {
                                t.printStackTrace();
                            }
                        });
                break;
            case 9:
            case 10:
                isWord = false;
                APIWorker.getInstance()
                        .getJSONApi()
                        .getTranslationCollocationTask(sectionId, previousWordId)
                        .enqueue(new Callback<List<TranslationTask>>() {
                            @Override
                            public void onResponse(@NonNull Call<List<TranslationTask>> call, @NonNull Response<List<TranslationTask>> response) {
                                List<TranslationTask> items = response.body();
                                for (int i=0; i<items.size(); i++) {
                                    TranslationTask t = items.get(i);
                                    if(t.isRight()){
                                        TextView wordText = (TextView) findViewById(R.id.wordToTranslate);
                                        switch(taskType){
                                            case 9:
                                                wordText.setText(t.getEnWord());
                                                break;
                                            case 10:
                                                wordText.setText(t.getRuWord());
                                                break;
                                        }
                                        rightWordId = t.getWordId();
                                        rightButton = i;
                                        break;
                                    }
                                }
                                ArrayList<Button> buttons = new ArrayList<Button>();
                                buttons.add(findViewById(R.id.buttonAnswer1));
                                buttons.add(findViewById(R.id.buttonAnswer2));
                                buttons.add(findViewById(R.id.buttonAnswer3));
                                buttons.add(findViewById(R.id.buttonAnswer4));
                                for(int i = 0; i < 4; i++){
                                    switch(taskType){
                                        case 9:
                                            buttons.get(i).setText(items.get(i).getRuWord());
                                            break;
                                        case 10:
                                            buttons.get(i).setText(items.get(i).getEnWord());
                                            break;
                                    }
                                    //buttons.get(i).setId(items.get(i).getWordId());
                                }
                            }
                            @Override
                            public void onFailure(@NonNull Call<List<TranslationTask>> call, @NonNull Throwable t) {
                                t.printStackTrace();
                            }
                        });
                break;
            case 11:
                isWord = false;
                APIWorker.getInstance()
                        .getJSONApi()
                        .getMatchingCollocationTask(sectionId, previousWordId)
                        .enqueue(new Callback<List<TranslationTask>>() {
                            @Override
                            public void onResponse(@NonNull Call<List<TranslationTask>> call, @NonNull Response<List<TranslationTask>> response) {
                                List<TranslationTask> items = response.body();
                                for (int i=0; i<items.size(); i++) {
                                    TranslationTask t = items.get(i);
                                    if(t.isRight()){
                                        TextView wordText = (TextView) findViewById(R.id.wordToTranslate);
                                        wordText.setText(t.getEnWord() + " ...");
                                        rightButton = i;
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
                                    buttons.get(i).setText(items.get(i).getRuWord());
                                    //buttons.get(i).setId(items.get(i).getWordId());
                                }
                            }
                            @Override
                            public void onFailure(@NonNull Call<List<TranslationTask>> call, @NonNull Throwable t) {
                                t.printStackTrace();
                            }
                        });
                break;
        }
    }

    private int getIdFromIndex(int index){
        switch(index){
            case 0:
                return R.id.buttonAnswer1;
            case 1:
                return R.id.buttonAnswer2;
            case 2:
                return R.id.buttonAnswer3;
            default:
                return R.id.buttonAnswer4;
        }
    }

    @Override
    public void openNewActivity(View view){
        int result;
        if(view.getId() == getIdFromIndex(rightButton)){
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
        UserStatServerRequest request = new UserStatServerRequest(getAuthedUserId(), rightWordId, result, taskId);
        if(isWord)
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
        else
            APIWorker.getInstance().getJSONApi().fix_collocation_stat(request)
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