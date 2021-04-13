package com.example.englishapp.activities;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.englishapp.APIWorker;
import com.example.englishapp.R;
import com.example.englishapp.ServerResponse;
import com.example.englishapp.Word;
import com.example.englishapp.requests.WordServerRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WordCardActivity extends BaseActivity {
    @Override
    public void setView() {
        setContentView(R.layout.activity_word_card);
        Intent intent = getIntent();

        int word_id = intent.getIntExtra("word_id", 0);
        if(intent.hasExtra("message")){
            String mess = intent.getStringExtra("message");
            Toast toast = Toast.makeText(getContext(), mess, Toast.LENGTH_LONG);
            toast.show();
        }
        APIWorker.getInstance()
                .getJSONApi()
                .getWordById(word_id, 1)
                .enqueue(new Callback<Word>() {
                    @Override
                    public void onResponse(@NonNull Call<Word> call, @NonNull Response<Word> response) {
                        Word current_word = response.body();
                        TextView t=(TextView)findViewById(R.id.textView2);
                        t.setText(current_word.getEnWord());
                        t=(TextView)findViewById(R.id.textView3);
                        t.setText(current_word.getRuWord());
                        t=(TextView)findViewById(R.id.textView4);
                        t.setText(String.format("(%s) %s", current_word.getPartOfSpeech(), current_word.getDefinition()));
                        if(current_word.isInDictionary()){
                            findViewById(R.id.button4).setVisibility(View.VISIBLE);
                            findViewById(R.id.button4).setId(word_id);
                            findViewById(R.id.textView6).setVisibility(View.VISIBLE);
                        }
                        else{
                            findViewById(R.id.button).setVisibility(View.VISIBLE);
                            findViewById(R.id.button).setId(word_id);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Word> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    @Override
    public void openNewActivity(View view) {
        int wordId = view.getId();
        WordServerRequest request = new WordServerRequest(1, wordId);
        APIWorker.getInstance()
                .getJSONApi()
                .addToDictionary(request)
                .enqueue(new Callback<ServerResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {
                        ServerResponse word = response.body();
                        Intent intent = getIntent();
                        intent.putExtra("message","'" + word.getMessage() +"' added to your dictionary");
                        finish();
                        startActivity(intent);

                    }

                    @Override
                    public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public void removeWord(View view) {
        int wordId = view.getId();
        WordServerRequest request = new WordServerRequest(1, wordId);
        APIWorker.getInstance()
                .getJSONApi()
                .remove_from_dictionary(request)
                .enqueue(new Callback<ServerResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {
                        ServerResponse word = response.body();
                        Intent intent = getIntent();
                        intent.putExtra("message","'" + word.getMessage() +"' removed from your dictionary");
                        finish();
                        startActivity(intent);

                    }

                    @Override
                    public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

}