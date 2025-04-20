package com.example.tma;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TaskDetailActivity extends AppCompatActivity {

    TextView taskNameTextView, dueDateTextView, statusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        taskNameTextView = findViewById(R.id.taskDetailName);
        dueDateTextView = findViewById(R.id.taskDetailDueDate);
        statusTextView = findViewById(R.id.taskDetailStatus);

        // Get data from Intent
        String taskName = getIntent().getStringExtra("taskName");
        String dueDate = getIntent().getStringExtra("dueDate");
        boolean isCompleted = getIntent().getBooleanExtra("status", false);

        // Set data to views
        taskNameTextView.setText(taskName);
        dueDateTextView.setText("Due: " + dueDate);
        statusTextView.setText("Status: " + (isCompleted ? "Completed" : "Pending"));
    }
}
