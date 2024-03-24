package com.example.gp3assignment2;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import com.example.gp3assignment2.databinding.ActivityAddTaskBinding;

public class AddTask extends AppCompatActivity {

    ActivityAddTaskBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}