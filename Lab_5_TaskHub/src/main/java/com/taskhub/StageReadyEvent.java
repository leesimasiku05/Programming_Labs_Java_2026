package com.taskhub;

import javafx.stage.Stage;

public class StageReadyEvent {

    private final Stage stage;

    public StageReadyEvent(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }
}
