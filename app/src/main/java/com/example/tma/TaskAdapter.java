package com.example.tma;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Tasks> taskList;



    public TaskAdapter(List<Tasks> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Tasks task = taskList.get(position);
//        holder.textViewTask.setText(task.getTaskName());
        holder.taskName.setText(task.getTaskName());
        holder.taskDue.setText("Due: " + task.getDueDate());
        holder.taskCheckbox.setChecked(task.isCompleted());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), TaskDetailActivity.class);
            intent.putExtra("taskName", task.getTaskName());
            intent.putExtra("dueDate", task.getDueDate());
            intent.putExtra("status", task.isCompleted());
            v.getContext().startActivity(intent);
        });
        holder.taskCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setCompleted(isChecked); // update locally
            FirebaseFirestore.getInstance()
                    .collection("tasks")
                    .document(task.getId())
                    .update("completed", isChecked)
                    .addOnSuccessListener(aVoid -> {
                        // Optionally notify UI or log
                    })
                    .addOnFailureListener(e -> {
                        // Handle failure (e.g., revert checkbox)
                        buttonView.setChecked(!isChecked);
                    });
        });



    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskName, taskDue;
        TextView textViewTask;
        CheckBox taskCheckbox;



        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.taskName);
//            textViewTask = itemView.findViewById(android.R.id.text1);
            taskDue = itemView.findViewById(R.id.task_due);
            taskCheckbox = itemView.findViewById(R.id.task_checkbox);

        }
    }
}
