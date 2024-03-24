package com.example.gp3assignment2;

import java.sql.Time;
import java.util.Date;

public class Task {
    String taskName;
    String taskDate;
    Time taskTime;

    String taskId;

    public Task(String taskId,String taskName, String taskDate, Time taskTime) {
        this.taskName = taskName;
        this.taskDate = taskDate;
        this.taskTime = taskTime;
        this.taskId = taskId;

    }

    public Task(){

    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public Time getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(Time taskTime) {
        this.taskTime = taskTime;
    }
}
