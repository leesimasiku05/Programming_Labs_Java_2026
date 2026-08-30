package com.taskhub;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StageInitializer {

    private final TaskController taskController;

    public StageInitializer(TaskController taskController) {
        this.taskController = taskController;
    }

    @EventListener
    public void onStageReady(StageReadyEvent event) {

        try {

            Stage stage = event.getStage();

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ui.fxml")
            );

            loader.setControllerFactory(
                    type -> {

                        if (type == TaskController.class) {
                            return taskController;
                        }

                        return null;
                    }
            );

            Parent root = loader.load();

            Scene scene = new Scene(root);

            stage.setTitle("TaskHub");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}