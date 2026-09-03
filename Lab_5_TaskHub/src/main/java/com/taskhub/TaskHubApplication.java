package com.taskhub;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TaskHubApplication extends Application {

    private ConfigurableApplicationContext springContext;

    @Override
    public void init() {
        // Start the Spring Boot application first
        springContext = SpringApplication.run(TaskHubApplication.class);
    }

    @Override
    public void start(Stage stage) {
        // Send the JavaFX stage into the Spring application
        springContext.publishEvent(new StageReadyEvent(stage));
    }

    @Override
    public void stop() {
        // Shut down Spring when JavaFX closes
        if (springContext != null) {
            springContext.close();
        }

        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }
}