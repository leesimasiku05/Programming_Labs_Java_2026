package com.taskhub;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TaskHubApplication extends Application {

    private ConfigurableApplicationContext springContext;

    @Override
    public void init() {
        springContext = org.springframework.boot.SpringApplication.run(
                TaskHubApplication.class
        );
    }

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                TaskHubApplication.class.getResource("/ui.fxml")
        );

        // Tell JavaFX to get controllers from Spring
        loader.setControllerFactory(springContext::getBean);

        Parent root = loader.load();

        Scene scene = new Scene(root, 500, 400);

        stage.setTitle("TaskHub");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        if (springContext != null) {
            springContext.close();
        }

        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

