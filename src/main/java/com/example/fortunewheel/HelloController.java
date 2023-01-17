package com.example.fortunewheel;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class HelloController {
    @FXML
    private Label round;
    @FXML
    private HBox hbox;

    @FXML
    protected void onHelloButtonClick() {
        round.setText("Welcome to JavaFX Application!");
    }
}