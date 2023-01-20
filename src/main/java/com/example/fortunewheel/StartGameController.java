package com.example.fortunewheel;

import back.MainInterface;
import back.handlers.players.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class StartGameController implements MainInterface {

    @FXML
    TextField nameTextField;

    public void changeScene(ActionEvent event) throws IOException {

        if (!nameTextField.getText().isBlank()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gameView.fxml"));
            Parent parent = loader.load();
            PlayerController playerController = loader.getController();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("Wheel of fortune");
            window.setScene(new Scene(parent));
            window.show();
            new Thread(() -> {
                Scanner scanner = new Scanner(System.in);
                Socket socket = null;
                try {
                    socket = new Socket(PROXY, PORT_NUMBER);
                } catch (IOException e) {
                    System.out.println("Error creating a socket for client " + e.getMessage());
                }
                Player player = new Player(socket, nameTextField.getText(), scanner);
                playerController.initData(player);
            }).start();
        }
    }
}