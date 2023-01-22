package com.fortunewheel.frontend;

import com.fortunewheel.backend.MainInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PlayerApplication extends Application implements MainInterface {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("startGameView.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Wheel of fortune Game");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
