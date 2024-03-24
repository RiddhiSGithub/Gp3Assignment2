package com.example.gp3assignment2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.gp3assignment2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate layout using view binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnListTask.setOnClickListener(this);
        binding.btnAddTask.setOnClickListener(this);
        SharedPreferences sharedPreferences = getSharedPreferences("Tasks", MODE_PRIVATE);
        Log.e("taskdetails", sharedPreferences.getAll().toString());


    }

    @Override
    public void onClick(View v) {
        if(v.getId() == binding.btnAddTask.getId()){
            startActivity(new Intent(MainActivity.this, AddTask.class));
            finish();
        }else if(v.getId() == binding.btnListTask.getId()){
            startActivity(new Intent(MainActivity.this, Tasks.class));
            finish();
        }
    }
}