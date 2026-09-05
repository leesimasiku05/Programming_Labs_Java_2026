package com.taskhub;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final List<Task> tasks = new ArrayList<>();

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(String description) {
        if (description != null && !description.trim().isEmpty()) {
            tasks.add(new Task(description.trim()));
        }
    }
}