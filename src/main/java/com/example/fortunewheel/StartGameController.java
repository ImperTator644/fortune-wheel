package com.example.fortunewheel;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class StartGameController  {


    public void changeScene(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("gameView.fxml"));
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Wheel of fortune");
        window.setScene(new Scene(parent));
        window.show();

    }

}