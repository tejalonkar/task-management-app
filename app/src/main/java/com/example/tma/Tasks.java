package com.example.tma;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



public class Tasks {
    private String id;
    private String taskName;
    private String dueDate;
    private boolean completed;

    // Empty constructor required for Firestore
    public Tasks() {}

    public Tasks(String id, String taskName, String dueDate, boolean completed) {
        this.id = id;
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.completed = completed;

    }

    public String getId() { return id; }
    public String getTaskName() { return taskName; }
    public String getDueDate() { return dueDate; }
    public boolean isCompleted() { return completed; }

    public void setCompleted(boolean completed) { this.completed = completed; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }

}
