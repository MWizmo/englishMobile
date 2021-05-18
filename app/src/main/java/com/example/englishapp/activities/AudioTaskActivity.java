package com.example.englishapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
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

public class AudioTaskActivity extends BaseActivity {

    private int rightWordId;
    private int rightButton;
    private int taskId;

    @Override
    public void setView() {
        setContentView(R.layout.activity_audio_task);
        Intent intent = getIntent();
        int sectionId = intent.getIntExtra("sectionId", 0);
        int taskType = intent.getIntExtra("taskType", 0);
        taskId = intent.getIntExtra("taskId", 0);
        TextView title = (TextView) findViewById(R.id.taskTitleA);
        int previousWordId = intent.getIntExtra("previousWordId", 0);
        switch(taskType){
            case 6:
                title.setText("Choose the right word by audio");
                break;
            case 7:
                title.setText("Choose the right translation by audio");
                break;
        }
        APIWorker.getInstance()
                .getJSONApi()
                .getAudioTask(sectionId, previousWordId)
                .enqueue(new Callback<List<TranslationTask>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<TranslationTask>> call, @NonNull Response<List<TranslationTask>> response) {
                        List<TranslationTask> items = response.body();
                        for (int i=0; i<items.size(); i++) {
                            TranslationTask t = items.get(i);
                            if (t.isRight()) {
                                rightWordId = t.getWordId();
                                rightButton = i;
                                break;
                            }
                        }
                        ArrayList<Button> buttons = new ArrayList<Button>();
                        buttons.add(findViewById(R.id.buttonAnswer1A));
                        buttons.add(findViewById(R.id.buttonAnswer2A));
                        buttons.add(findViewById(R.id.buttonAnswer3A));
                        buttons.add(findViewById(R.id.buttonAnswer4A));
                        for (int i = 0; i < 4; i++) {
                            switch (taskType) {
                                case 6:
                                    buttons.get(i).setText(items.get(i).getEnWord());
                                    break;
                                case 7:
                                    buttons.get(i).setText(items.get(i).getRuWord());
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<TranslationTask>> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    private int getIdFromIndex(int index){
        switch(index){
            case 0:
                return R.id.buttonAnswer1A;
            case 1:
                return R.id.buttonAnswer2A;
            case 2:
                return R.id.buttonAnswer3A;
            default:
                return R.id.buttonAnswer4A;
        }
    }

    public void playAudio(View view){
        MediaPlayer mp = new MediaPlayer();
        String audioUrl = APIWorker.BASE_URL + "audio_file/words/" + String.valueOf(rightWordId);
        try {
            mp.setDataSource(audioUrl);
            mp.prepare();
            mp.start();
        } catch (IOException e) {

        }
    }

    @Override
    public void openNewActivity(View view) throws IOException {
        int result;
        if(view.getId() == getIdFromIndex(rightButton)){
            Toast toast = Toast.makeText(getContext(), "Right !!!", Toast.LENGTH_SHORT);
            toast.show();
            result = 1;
            findViewById(R.id.newWordButtonA).setVisibility(View.VISIBLE);
        }
        else{
            Toast toast = Toast.makeText(getContext(), "Wrong (((", Toast.LENGTH_SHORT);
            toast.show();
            result = 0;
            findViewById(R.id.newWordButtonA).setVisibility(View.INVISIBLE);
        }
        UserStatServerRequest request = new UserStatServerRequest(getAuthedUserId(), rightWordId, result, taskId);
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
        Intent newIntent = new Intent(getContext(), AudioTaskActivity.class);
        newIntent.putExtra("sectionId", curIntent.getIntExtra("sectionId", 0));
        newIntent.putExtra("taskId", taskId);
        newIntent.putExtra("taskType", curIntent.getIntExtra("taskType", 0));
        newIntent.putExtra("previousWordId", rightWordId);
        finish();
        startActivity(newIntent);
    }
}