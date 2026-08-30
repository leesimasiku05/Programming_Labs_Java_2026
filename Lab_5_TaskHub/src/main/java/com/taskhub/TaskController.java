package com.taskhub;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.stereotype.Component;

@Component
public class TaskController {

    private final TaskService taskService;

    @FXML
    private Label statusLabel;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @FXML
    public void initialize() {
        System.out.println("TaskController created successfully.");

        if (statusLabel != null) {
            statusLabel.setText("TaskHub is ready!");
        }
    }

    @FXML
    private void handleCreateTask() {
        System.out.println("Create Task button clicked.");

        if (statusLabel != null) {
            statusLabel.setText("Creating task...");
        }

        taskService.createTask();
    }

    @FXML
    private void handleViewTasks() {
        System.out.println("View Tasks button clicked.");

        if (statusLabel != null) {
            statusLabel.setText("Viewing tasks...");
        }

        taskService.viewTasks();
    }
}

