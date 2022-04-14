package com.example.stevenclassproject.controllers;

import com.example.stevenclassproject.services.MemberServices;
import com.example.stevenclassproject.services.TaskServices;

public class TaskController {

    private final TaskServices taskServices;

    public TaskController (TaskServices taskServices) {
        this.taskServices = taskServices;
    }
}
