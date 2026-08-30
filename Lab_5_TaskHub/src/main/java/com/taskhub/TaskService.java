package com.taskhub;

import org.springframework.stereotype.Service;

@Service
public class TaskService {

    public void createTask() {
        System.out.println("Creating task...");
    }

    public void viewTasks() {
        System.out.println("Viewing tasks...");
    }
}

