package com.example.alexkeith.taskmanager;

import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

public class Task {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String taskName;
    private String taskDescription;
    private boolean isCompletedOut;
    private Date dueDate;


    public Task(String taskName, String taskDescription, Date dueDate) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.dueDate = dueDate;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setGameGenre(String gameGenre) {
        this.gameGenre = gameGenre;
    }

    public boolean isCheckedOut() {
        return isCheckedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        isCheckedOut = checkedOut;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
