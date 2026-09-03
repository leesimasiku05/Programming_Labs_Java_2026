package com.taskhub;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskController {

    private final TaskService taskService;

    @FXML
    private ListView<String> taskListView;

    @FXML
    private TextField taskTextField;

    @FXML
    private Label statusLabel;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @FXML
    public void initialize() {

        refreshTaskList();

        statusLabel.setText(
                taskService.getTasks().size() + " tasks available"
        );
    }

    @FXML
    private void handleAddTask() {

        String taskDescription = taskTextField.getText();

        if (taskDescription == null ||
                taskDescription.trim().isEmpty()) {

            statusLabel.setText("Please enter a task.");

            return;
        }

        taskService.addTask(taskDescription);

        refreshTaskList();

        taskTextField.clear();

        statusLabel.setText("Task added successfully.");
    }

    private void refreshTaskList() {

        taskListView.setItems(
                FXCollections.observableArrayList(
                        taskService.getTasks()
                )
        );
    }
}