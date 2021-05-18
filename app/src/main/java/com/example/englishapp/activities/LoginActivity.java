package com.example.englishapp.activities;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.englishapp.APIWorker;
import com.example.englishapp.Constants;
import com.example.englishapp.R;
import com.example.englishapp.ServerResponse;
import com.example.englishapp.requests.LoginRequest;
import com.example.englishapp.requests.UserStatServerRequest;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
        if(preferences.contains(Constants.APP_PREFERENCES_USER_ID)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
        }
    }

    public void login(View view) {
        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);
        if (username.getText().length() == 0 ) {
            Toast.makeText(getApplicationContext(), "Enter username", Toast.LENGTH_SHORT).show();
        }
        else{
            if (password.getText().length() == 0 ) {
                Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_SHORT).show();
            }
            else{
                LoginRequest request = new LoginRequest(username.getText().toString(), password.getText().toString());
                APIWorker.getInstance().getJSONApi().login(request)
                        .enqueue(new Callback<ServerResponse>() {
                            @Override
                            public void onResponse(@NonNull Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {
                                ServerResponse serverResponse = response.body();
                                if (serverResponse.getStatus() == 400)
                                    Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_LONG).show();
                                else{
                                    SharedPreferences preferences = getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    String[] words = serverResponse.getMessage().split("_");
                                    editor.putInt(Constants.APP_PREFERENCES_USER_ID, Integer.parseInt(words[0]));
                                    editor.putString(Constants.APP_PREFERENCES_USERNAME, words[1]);
                                    editor.apply();
                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {
                                t.printStackTrace();
                            }
                        });
            }
        }
    }

    public void googleLogin(View view){

    }

    public Context getContext() {
        return (Context)this;
    }
}