package com.taskhub;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class TaskHubApplication extends Application {

    private static ConfigurableApplicationContext springContext;

    @Override
    public void init() {

        springContext = SpringApplication.run(
                TaskHubApplication.class
        );
    }

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/ui.fxml")
        );

        // Get the controller from Spring
        loader.setControllerFactory(
                springContext::getBean
        );

        Parent root = loader.load();

        Scene scene = new Scene(root);

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

