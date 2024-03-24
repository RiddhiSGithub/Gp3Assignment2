package com.example.gp3assignment2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.gp3assignment2.databinding.ActivityTasksBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

public class PendingTasks extends AppCompatActivity {
    ActivityTasksBinding binding;

    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    List<Task> taskList; // List to hold data for RecyclerView

    SharedPreferences sharedPreferences; // SharedPreferences to store workout sessions
    SharedPreferences.Editor editor; // SharedPreferences editor
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityTasksBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("Tasks", Context.MODE_PRIVATE);

        recyclerView = findViewById(R.id.recycler_view_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        taskList = new ArrayList<>();


        sharedPreferences.getAll().forEach((key, value) -> {
            Task tempTask = getTaskList(getApplicationContext(),key).get(0);
            if(checkTimeGap(tempTask.getTaskTime()))
            {
                taskList.add(tempTask);
            }

        });
        adapter = new TaskAdapter(taskList);
        recyclerView.setAdapter(adapter);
    }

    public static List<Task> getTaskList(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Tasks", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(key, null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Task>>() {}.getType();
        return gson.fromJson(json, type);
    }

    private boolean checkTimeGap(Time taskTime) {
        Calendar calendar = Calendar.getInstance();
        Time currentTime = new Time(calendar.getTimeInMillis());

        // Given time
        Time givenTime = Time.valueOf(String.valueOf(taskTime));

        // Calculate the difference in milliseconds
        long timeDifference = givenTime.getTime() - currentTime.getTime();

        // Convert the difference to hours
        long differenceInHours = timeDifference / (60 * 60 * 1000);

        // Check if the difference is less than or equal to one hour
        if (differenceInHours <= 1) {
            return true;
        } else {
            return false;
        }
    }
}