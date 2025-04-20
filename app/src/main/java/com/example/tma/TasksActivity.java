package com.example.tma;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.List;

public class TasksActivity extends AppCompatActivity {

    private EditText editTextTask;
    private Button buttonAddTask;
    private RecyclerView recyclerViewTasks;
    private TaskAdapter taskAdapter;
    private List<Tasks> taskList;

    private FirebaseFirestore db;
    private CollectionReference tasksRef;
    private EditText editTextDueDate;


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

        editTextDueDate = findViewById(R.id.editTextDueDate);
        editTextDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(TasksActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int selYear, int selMonth, int selDay) {
                                String formattedDate = selYear + "-" +
                                        String.format("%02d", (selMonth + 1)) + "-" +
                                        String.format("%02d", selDay);

                                editTextDueDate.setText(formattedDate);
                            }
                        }, year, month, day);

                datePickerDialog.show();
            }
        });




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
        String dueDate = editTextDueDate.getText().toString().trim();

        if (taskName.isEmpty() || dueDate.isEmpty()) {
            Toast.makeText(this, "Enter task name and select a due date", Toast.LENGTH_SHORT).show();
            return;
        }

        String taskId = tasksRef.document().getId();
        Tasks task = new Tasks(taskId, taskName, dueDate, false);

        tasksRef.document(taskId).set(task)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Task added!", Toast.LENGTH_SHORT).show();
                    editTextTask.setText("");
                    editTextDueDate.setText("");
                    loadTasks();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed to add task", Toast.LENGTH_SHORT).show());
    }
}
