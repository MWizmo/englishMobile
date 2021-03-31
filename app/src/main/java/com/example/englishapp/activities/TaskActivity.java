package com.example.englishapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.englishapp.enums.TaskType;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.englishapp.APIWorker;
import com.example.englishapp.R;
import com.example.englishapp.Section;
import com.example.englishapp.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskActivity extends BaseActivity {
    @Override
    public void setView() {
        Intent intent = getIntent();
        int taskId = intent.getIntExtra("task_id", 0);
        int taskType = intent.getIntExtra("taskType", 0);
        switch (taskType){
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
        setContentView(R.layout.activity_task);
    }

    @Override
    public void openNewActivity(View view) {

    }
}