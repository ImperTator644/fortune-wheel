package com.example.fortunewheel;

import back.MainInterface;
import back.handlers.players.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class PlayerApplication extends Application implements MainInterface {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("startGameView.fxml"));
        primaryStage.setTitle("Wheel of fortune Game");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        new Thread(() -> launch(args)).start();

        Scanner scanner = new Scanner(System.in);
        Socket socket = null;
        try {
            socket = new Socket(PROXY, PORT_NUMBER);
        } catch (IOException e) {
            System.out.println("Error creating a socket for client " + e.getMessage());
        }
        Player player = new Player(socket, setUserName(scanner), scanner);
    }

    private static String setUserName(Scanner scanner) {
        System.out.println("Podaj imie: ");
        return scanner.nextLine();
    }
}
