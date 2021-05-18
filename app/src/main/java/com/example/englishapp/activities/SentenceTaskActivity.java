package com.example.englishapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.englishapp.APIWorker;
import com.example.englishapp.MissingWord;
import com.example.englishapp.R;
import com.example.englishapp.SentenceTask;
import com.example.englishapp.ServerResponse;
import com.example.englishapp.TranslationTask;
import com.example.englishapp.requests.UserStatServerRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SentenceTaskActivity extends BaseActivity {

    private int taskId;
    private List<MissingWord> words;
    private int currentOrder;
    private int blanks;
    private int sentenceId;

    @Override
    public void setView() {
        setContentView(R.layout.activity_sentence_task);
        Intent intent = getIntent();
        int sectionId = intent.getIntExtra("sectionId", 0);
        taskId = intent.getIntExtra("taskId", 0);
        currentOrder = 1;
        int previousSentenceId = intent.getIntExtra("previousSentenceId", 0);
        APIWorker.getInstance()
            .getJSONApi()
            .getSentenceTask(sectionId, previousSentenceId)
            .enqueue(new Callback<SentenceTask>() {
                @SuppressLint("ResourceType")
                @Override
                public void onResponse(@NonNull Call<SentenceTask> call, @NonNull Response<SentenceTask> response) {
                    SentenceTask task = response.body();
                    words = task.getWords();
                    blanks = task.getBlanks();
                    sentenceId = task.getSentenceId();
                    TextView wordText = (TextView) findViewById(R.id.sentenceText);
                    wordText.setText(task.getSentence());
                    ArrayList<Button> buttons = new ArrayList<Button>();
                    buttons.add(findViewById(R.id.buttonAnswer11));
                    buttons.add(findViewById(R.id.buttonAnswer22));
                    buttons.add(findViewById(R.id.buttonAnswer33));
                    buttons.add(findViewById(R.id.buttonAnswer44));
                    for (int i = 0; i < 4; i++) {
                        buttons.get(i).setText(words.get(i).getWord());
                    }
                }
                @Override
                public void onFailure(@NonNull Call<SentenceTask> call, @NonNull Throwable t) {
                    t.printStackTrace();
                }
            });

    }

    private int getIndexFromId(int id){
        switch(id){
            case R.id.buttonAnswer11:
                return 0;
            case R.id.buttonAnswer22:
                return 1;
            case R.id.buttonAnswer33:
                return 2;
            default:
                return 3;
        }
    }

    @Override
    public void openNewActivity(View view) throws IOException {
        int wordId = getIndexFromId(view.getId());
        MissingWord word = words.get(wordId);
        if(word.getOrder() == currentOrder){
            TextView wordText = (TextView) findViewById(R.id.sentenceText);
            String text = (String) wordText.getText();
            String newText = text.replaceFirst("___", word.getWord());
            wordText.setText(newText);
            view.setClickable(false);
            if(currentOrder == blanks){
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
                Toast toast = Toast.makeText(getContext(), "Right!", Toast.LENGTH_SHORT);
                toast.show();
                findViewById(R.id.newSentenceButton).setVisibility(View.VISIBLE);
            }
            else
                currentOrder ++;
        }
        else{
            Toast toast = Toast.makeText(getContext(), "Wrong (((", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void newSentence(View view){
        Intent curIntent = getIntent();
        Intent newIntent = new Intent(getContext(), SentenceTaskActivity.class);
        newIntent.putExtra("sectionId", curIntent.getIntExtra("sectionId", 0));
        newIntent.putExtra("taskId", taskId);
        newIntent.putExtra("previousSentenceId", sentenceId);
        finish();
        startActivity(newIntent);
    }
}