package com.example.englishapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.englishapp.Constants;
import com.example.englishapp.R;

import java.io.IOException;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public Context getContext() {
        return (Context)this;
    }

    public abstract void setView();

    public abstract void openNewActivity(View view) throws IOException;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        if (item.getItemId() == R.id.action_logout){
            SharedPreferences preferences = getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove(Constants.APP_PREFERENCES_USER_ID);
            editor.remove(Constants.APP_PREFERENCES_USERNAME);
            editor.apply();
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.menuUsername);
        SharedPreferences preferences = getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
        item.setTitle(preferences.getString(Constants.APP_PREFERENCES_USERNAME, "Unknown"));
        return super.onPrepareOptionsMenu(menu);
    }

    protected int getAuthedUserId(){
        SharedPreferences preferences = getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
        return preferences.getInt(Constants.APP_PREFERENCES_USER_ID, 0);
    }
}
