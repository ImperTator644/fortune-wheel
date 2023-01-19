package back.handlers.players;

import back.game.Functions;
import back.handlers.Handler;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Player extends Handler {
    private final Scanner scanner;
    private final String userName;

    public Player(Socket socket, String userName, Scanner scanner) {
        super(socket);
        this.scanner = scanner;
        this.listenForMessage();
        this.userName = userName;
        sendMessage(userName);
        this.test();
        System.out.println("Connected to server");
    }

    private void test(){
        while(socket.isConnected()){
            String message = scanner.nextLine();
            sendMessage(createProperMessage(Functions.LETTER, message));
        }
    }

    private void listenForMessage(){
        new Thread(() -> {
            String msg;
            while(socket.isConnected()){
                try {
                    msg = reader.readLine();
                    System.out.println(msg);
                } catch (IOException e) {
                    System.out.println("Error receiving message from other clients");
                }
            }
        }).start();
    }

    private void sendMessage(String message) {
        try{
            this.writer.write(message);
            this.writer.newLine();
            this.writer.flush();
            System.out.println("Message " + message + " sent");
        }
        catch (IOException e){
            System.out.println("Error reading question " + e.getMessage());
            closeEverything();
        }
    }

    private String createProperMessage(Functions functions, String message){
        return String.join(":", userName, functions.getFunction(), message);
    }
}
