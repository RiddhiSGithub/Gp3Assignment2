package com.example.gp3assignment2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    private List<Task> taskList; // List to hold data for each session

    // Constructor to initialize the adapter with the list of session data
    public TaskAdapter(List<Task> dataList) {
        this.taskList = dataList;
    }

    // Create ViewHolder instances
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the item layout and create a new ViewHolder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item_layout, parent, false);
        return new MyViewHolder(view);
    }

    // Bind data to the ViewHolder
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.txtTaskId.setText(task.getTaskId());
        holder.txtTaskName.setText(task.getTaskName());
        holder.txtTaskDateTime.setText(task.getTaskDate() + " " + task.getTaskTime());
    }

    // Return the total number of items in the data list
    @Override
    public int getItemCount() {
        return taskList.size();
    }

    // ViewHolder class to hold the views for each item
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTaskId; // TextView to display session data
        public TextView txtTaskName; // TextView to display session data
        public TextView txtTaskDateTime; // TextView to display session data

        // Constructor to initialize the ViewHolder with the item view
        public MyViewHolder(View itemView) {
            super(itemView);
            txtTaskId = itemView.findViewById(R.id.text_task_id); // Initialize TextView from item layout
            txtTaskName = itemView.findViewById(R.id.text_task_name); // Initialize TextView from item layout
            txtTaskDateTime = itemView.findViewById(R.id.text_due_date_time); // Initialize TextView from item layout
        }
    }
}
