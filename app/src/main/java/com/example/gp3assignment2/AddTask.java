package com.example.gp3assignment2;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.gp3assignment2.databinding.ActivityAddTaskBinding;
import com.google.gson.Gson;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.List;
import java.util.Locale;


public class AddTask extends AppCompatActivity
        implements
        View.OnClickListener {

    ActivityAddTaskBinding binding;

    String taskName;
    Date taskDate;
    Time taskTime;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSaveTask.setOnClickListener(this);
        binding.editTime.setOnClickListener(this);
        binding.editDueDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.btnSaveTask.getId()) {
            getUserDetails();

            if (userDataValidated()) {
                sharedPreferences = getSharedPreferences("Tasks", Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
                int size = sharedPreferences.getAll().size();
                String key = "Task-" + (size + 1);


                List<Task> tasks = new ArrayList<>();
                tasks.add(new Task(key, taskName, taskDate.toString(), taskTime));
                saveTaskList(getApplicationContext(), tasks, key);

                if(checkTimeGap())
                {
                    createNotificationChannel(key);
                }

                clearAll();

                // Start PreviousSessionActivity to display saved sessions
                Intent intent = new Intent(AddTask.this, Tasks.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Please fill up all the data", Toast.LENGTH_SHORT).show();
            }

        } else if (v.getId() == binding.editDueDate.getId()) {
            openDatePicker();

        } else if (v.getId() == binding.editTime.getId()) {
            openTimePicker();
        }


    }

    private boolean checkTimeGap() {
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

    public static void saveTaskList(Context context, List<Task> taskList, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Tasks", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(taskList);
        editor.putString(key, json);
        editor.apply();
    }


    private boolean userDataValidated() {
        if (binding.editTaskName.length() == 0)
            return false;
        else if (binding.editTime.length() == 0)
            return false;
        else if (binding.editDueDate.length() == 0)
            return false;
        else
            return true;
    }

    private void openTimePicker() {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minuteOfDay) -> {

            binding.editTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minuteOfDay));
            c.add(Calendar.MINUTE, minuteOfDay);
            c.add(Calendar.HOUR_OF_DAY, hourOfDay);
            taskTime = new Time(c.getTimeInMillis());
        }, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    private void openDatePicker() {
        DatePickerDialog datePickerDialog;
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {

            binding.editDueDate.setText(selectedDayOfMonth + "/" + (selectedMonth + 1) + "/" + selectedYear);
            c.set(Calendar.YEAR, selectedYear);
            c.set(Calendar.MONTH, selectedMonth);
            c.set(Calendar.DAY_OF_MONTH, selectedDayOfMonth);
            taskDate = new Date(c.getTimeInMillis());

        }, year, month, day);
        datePickerDialog.show();
    }

    private void getUserDetails() {
        taskName = String.valueOf(binding.editTaskName.getText());
    }

    private void clearAll() {
        binding.editTaskName.setText(null);
        binding.editDueDate.setText(null);
        binding.editTime.setText(null);
    }
    private void createNotificationChannel(CharSequence key) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String id = "my_channel_01";

            String description = "Notification for Wear OS";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(id, "Pending Tasks", importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            NotificationManager nM = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

            Intent intent = new Intent(getApplicationContext(), PendingTasks.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            // Create a notification
            Notification.Builder builder = new Notification.Builder(getApplicationContext(), id)
                    .setContentTitle("Pending Tasks")
                    .setContentText(key + " " + taskName)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_notification)
                            .setAutoCancel(true);

            // Send the notification
            notificationManager.notify(1, builder.build());
        }

    }
}
