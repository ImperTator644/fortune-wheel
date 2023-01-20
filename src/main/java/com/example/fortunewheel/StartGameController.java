package com.example.fortunewheel;

import back.MainInterface;
import back.handlers.players.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class StartGameController implements MainInterface {


    public void changeScene(ActionEvent event) throws IOException {

        //FXMLLoader wheelLoader = new FXMLLoader(getClass().getResource(".fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gameView.fxml"));
        Parent parent = loader.load();// FXMLLoader.load(getClass().getResource("gameView.fxml"));

        PlayerController playerController = loader.getController();

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
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
            Player player = new Player(socket, setUserName(scanner), scanner);
            playerController.initData(player);
        }).start();


    }

    private static String setUserName(Scanner scanner) {
        System.out.println("Podaj imie: ");
        return scanner.nextLine();
    }
}