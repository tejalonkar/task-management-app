package com.example.tma;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TasksActivity extends AppCompatActivity {

    private EditText editTextTask;
    private Button buttonAddTask;
    private RecyclerView recyclerViewTasks;
    private TaskAdapter taskAdapter;
    private List<Tasks> taskList;

    private FirebaseFirestore db;
    private CollectionReference tasksRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        editTextTask = findViewById(R.id.editTextTask);
        buttonAddTask = findViewById(R.id.buttonAddTask);
        recyclerViewTasks = findViewById(R.id.recyclerViewTasks);

        db = FirebaseFirestore.getInstance();
        tasksRef = db.collection("tasks");

        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList);
        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTasks.setAdapter(taskAdapter);

        loadTasks();

        buttonAddTask.setOnClickListener(v -> addTask());
    }

    private void loadTasks() {
        tasksRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                taskList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Tasks taskItem = document.toObject(Tasks.class);
                    taskList.add(taskItem);
                }
                taskAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Failed to load tasks", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addTask() {
        String taskName = editTextTask.getText().toString().trim();

        if (taskName.isEmpty()) {
            Toast.makeText(this, "Enter a task", Toast.LENGTH_SHORT).show();
            return;
        }

        String taskId = tasksRef.document().getId();
        Tasks task = new Tasks(taskId, taskName);

        tasksRef.document(taskId).set(task).addOnSuccessListener(aVoid -> {
            Toast.makeText(this, "Task added!", Toast.LENGTH_SHORT).show();
            editTextTask.setText("");
            loadTasks(); // Refresh the list
        }).addOnFailureListener(e ->
                Toast.makeText(this, "Failed to add task", Toast.LENGTH_SHORT).show());
    }
}
