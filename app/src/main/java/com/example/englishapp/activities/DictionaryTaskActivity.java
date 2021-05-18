package com.example.englishapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.englishapp.APIWorker;
import com.example.englishapp.R;
import com.example.englishapp.ServerResponse;
import com.example.englishapp.TranslationTask;
import com.example.englishapp.requests.UserStatServerRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DictionaryTaskActivity extends BaseActivity {

    private int rightWordId;
    private boolean isWord;

    @Override
    public void setView() {
        setContentView(R.layout.activity_dictionary_task);
        Intent intent = getIntent();
        int taskType = intent.getIntExtra("taskType", 0);
        TextView title = (TextView) findViewById(R.id.taskTitleD);
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
                        .getDictionaryWordTask(getAuthedUserId(), previousWordId)
                        .enqueue(new Callback<List<TranslationTask>>() {
                            @Override
                            public void onResponse(@NonNull Call<List<TranslationTask>> call, @NonNull Response<List<TranslationTask>> response) {
                                List<TranslationTask> items = response.body();
                                for (TranslationTask t : items) {
                                    if (t.isRight()) {
                                        TextView wordText = (TextView) findViewById(R.id.wordToTranslateD);
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
                                        break;
                                    }
                                }
                                ArrayList<Button> buttons = new ArrayList<Button>();
                                buttons.add(findViewById(R.id.buttonAnswer1D));
                                buttons.add(findViewById(R.id.buttonAnswer2D));
                                buttons.add(findViewById(R.id.buttonAnswer3D));
                                buttons.add(findViewById(R.id.buttonAnswer4D));
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
                                    buttons.get(i).setId(items.get(i).getWordId());
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
                        .getDictionaryCollocationTask(getAuthedUserId(), previousWordId)
                        .enqueue(new Callback<List<TranslationTask>>() {
                            @Override
                            public void onResponse(@NonNull Call<List<TranslationTask>> call, @NonNull Response<List<TranslationTask>> response) {
                                List<TranslationTask> items = response.body();
                                for (TranslationTask t: items) {
                                    if(t.isRight()){
                                        TextView wordText = (TextView) findViewById(R.id.wordToTranslateD);
                                        switch(taskType){
                                            case 9:
                                                wordText.setText(t.getEnWord());
                                                break;
                                            case 10:
                                                wordText.setText(t.getRuWord());
                                                break;
                                        }
                                        rightWordId = t.getWordId();
                                        break;
                                    }
                                }
                                ArrayList<Button> buttons = new ArrayList<Button>();
                                buttons.add(findViewById(R.id.buttonAnswer1D));
                                buttons.add(findViewById(R.id.buttonAnswer2D));
                                buttons.add(findViewById(R.id.buttonAnswer3D));
                                buttons.add(findViewById(R.id.buttonAnswer4D));
                                for(int i = 0; i < 4; i++){
                                    switch(taskType){
                                        case 9:
                                            buttons.get(i).setText(items.get(i).getRuWord());
                                            break;
                                        case 10:
                                            buttons.get(i).setText(items.get(i).getEnWord());
                                            break;
                                    }
                                    buttons.get(i).setId(items.get(i).getWordId());
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

    @Override
    public void openNewActivity(View view){
        if(view.getId() == rightWordId){
            Toast toast = Toast.makeText(getContext(), "Right !!!", Toast.LENGTH_SHORT);
            toast.show();
            //findViewById(R.id.newWordButtonD).setVisibility(View.VISIBLE);
        }
        else{
            Toast toast = Toast.makeText(getContext(), "Wrong (((", Toast.LENGTH_SHORT);
            toast.show();
            //findViewById(R.id.newWordButtonD).setVisibility(View.INVISIBLE);
        }

    }

    public void newWord(View view){
        /*Intent curIntent = getIntent();
        Intent newIntent = new Intent(getContext(), TranslationTaskActivity.class);
        newIntent.putExtra("sectionId", curIntent.getIntExtra("sectionId", 0));
        newIntent.putExtra("taskId", taskId);
        newIntent.putExtra("taskType", curIntent.getIntExtra("taskType", 0));
        newIntent.putExtra("previousWordId", rightWordId);
        finish();
        startActivity(newIntent);*/
    }
}