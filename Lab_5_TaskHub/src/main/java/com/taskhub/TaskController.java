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
    private ListView<Task> taskListView;

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
        updateTaskCount();
    }

    @FXML
    private void handleAddTask() {

        String description = taskTextField.getText();

        if (description == null || description.trim().isEmpty()) {
            statusLabel.setText("Please enter a task.");
            return;
        }

        taskService.addTask(description);

        taskTextField.clear();

        refreshTaskList();
        updateTaskCount();

        statusLabel.setText("Task added successfully.");
    }

    @FXML
    private void handleTaskCheck() {

        Task selectedTask = taskListView.getSelectionModel().getSelectedItem();

        if (selectedTask == null) {
            statusLabel.setText("Select a task first.");
            return;
        }

        selectedTask.setCompleted(!selectedTask.isCompleted());

        refreshTaskList();
        updateTaskCount();

        statusLabel.setText(
                selectedTask.isCompleted()
                        ? "Task completed!"
                        : "Task marked as incomplete."
        );
    }

    private void refreshTaskList() {

        taskListView.setItems(
                FXCollections.observableArrayList(
                        taskService.getTasks()
                )
        );
    }

    private void updateTaskCount() {

        int taskCount = taskService.getTasks().size();

        statusLabel.setText(taskCount + " task(s)");
    }
}