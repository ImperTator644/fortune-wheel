package back.handlers.players;

import back.game.Functions;
import back.handlers.Handler;
import com.example.fortunewheel.PlayerController;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Player extends Handler {
    public final String userName;
    public Queue<String> messagesReceived;

    public Player(Socket socket, String userName) {
        super(socket);
        this.listenForMessage();
        this.userName = userName;
        sendMessage(userName);
        messagesReceived = new LinkedList<>();
        System.out.println("Connected to server");
    }

    private void listenForMessage(){
        new Thread(() -> {
            String msg;
            while(socket.isConnected()){
                try {
                    msg = reader.readLine();
                    messagesReceived.add(msg);
                    System.out.println("Buffer size: " + messagesReceived.size());
                    System.out.println(msg);
                } catch (IOException e) {
                    System.out.println("Error receiving message from other clients");
                }
            }
        }).start();
    }

    public void sendMessage(String message) {
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

    public String readMessageFromBuffer() {
        return messagesReceived.poll();
    }
}
