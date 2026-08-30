package com.taskhub;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final List<String> tasks = new ArrayList<>();

    public TaskService() {

        tasks.add("Fix Bug #102");
        tasks.add("Deploy to Production");
        tasks.add("Review Project Documentation");
    }

    public List<String> getTasks() {
        return tasks;
    }

    public void addTask(String description) {

        if (description != null && !description.trim().isEmpty()) {
            tasks.add(description);
        }
    }
}
