package com.taskhub;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskController {

    @FXML
    private ListView<String> taskListView;

    @FXML
    private TextField taskTextField;

    @Autowired
    private TaskService taskService;

    @FXML
    public void initialize() {

        refreshTaskList();
    }

    @FXML
    private void addTask() {

        String description = taskTextField.getText();

        if (description != null && !description.isBlank()) {

            taskService.addTask(description);

            taskTextField.clear();

            refreshTaskList();
        }
    }

    private void refreshTaskList() {

        taskListView.getItems().setAll(
                taskService.getTasks()
        );
    }
}