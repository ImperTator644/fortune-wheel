package com.example.fortunewheel;

import back.MainInterface;
import back.handlers.players.Player;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class PlayerController implements MainInterface {

    @FXML
    public TextField tf1;
    @FXML
    public TextField tf2;
    @FXML
    public TextField tf3;
    @FXML
    public TextField tf4;
    @FXML
    public TextField tf5;
    @FXML
    public TextField tf6;
    @FXML
    public TextField tf7;
    @FXML
    public TextField tf8;
    @FXML
    public TextField tf9;
    @FXML
    public TextField tf10;
    @FXML
    public ListView chatListView;
    boolean success = true;
    @FXML
    public TextField sentence;
    @FXML
    public TextField singleLetter;
    @FXML
    public Label category;
    private ObservableList<String> chatMessagesObservableList;


    private  String[] data = {"MEXICO COUNTRY", "HEDWIG BIRD", "KUAKATA BEACH", "CANADA COUNTRY", "DOCTOR PROFESSION", "FOOTBALL GAME", "TEACHER MENTOR", "LEOPARD ANIMAL", "BICYCLE TRANSPORT", "SALMON FISH", "SPARROW BIRD", "PARROTS BIRD", "EAGLE BIRD", "TRAIN TRANSPORT", "SHIP TRANSPORT", "ENGINEER PROFESSION", "BANKER PROFESSION", "CRICKET GAME"};
    private  int random = new Random().nextInt(data.length);
    private  String word_hint = data[random];
    private  String[] split = word_hint.split(" ", 2);
    public  String word = split[0];
    private String categ = split[1];
    private int letter_size = word.length();



    @FXML
    public ImageView wheelImageView;
    public Player player;

    @FXML
    public void initialize() {
/*        Scanner scanner = new Scanner(System.in);
        Socket socket = null;
        try {
            socket = new Socket(PROXY, PORT_NUMBER);
        } catch (IOException e) {
            System.out.println("Error creating a socket for client " + e.getMessage());
        }
        player = new Player(socket, setUserName(scanner), scanner);*/
        setHint();
    }

    public void initData(Player player) {
        System.out.println("Received player");
        this.player = player;
        listenToServer();
    }

    public void setHint() {
        category.setText(categ);
        if (letter_size == 9) {
            tf10.setVisible(false);
        }
        if (letter_size == 8) {
            tf9.setVisible(false);
            tf10.setVisible(false);
        }
        if (letter_size == 7) {
            tf8.setVisible(false);
            tf9.setVisible(false);
            tf10.setVisible(false);
        }
        if (letter_size == 6) {
            tf7.setVisible(false);
            tf8.setVisible(false);
            tf9.setVisible(false);
            tf10.setVisible(false);
        }
        if (letter_size == 5) {
            tf6.setVisible(false);
            tf7.setVisible(false);
            tf8.setVisible(false);
            tf9.setVisible(false);
            tf10.setVisible(false);
        }
        if (letter_size == 4) {
            tf5.setVisible(false);
            tf6.setVisible(false);
            tf7.setVisible(false);
            tf8.setVisible(false);
            tf9.setVisible(false);
            tf10.setVisible(false);
        }
    }

    public void CheckInputLetter() {

        String lett = singleLetter.getText();
        singleLetter.clear();

        if (word.contains(lett)) {
            success = true;
            int index = 0;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (String.valueOf(c).equals(lett)) {
                    setLetter(index, Character.toString(c));
                }
                index++;
            }
        }
        success = false;
    }
    public void CheckInputSentence(){

        String senten = sentence.getText();
        sentence.clear();
        if (word.equals(senten)){
            success = true;
            setSentence(senten);
        }
        success = false;
    }

    public void setLetter(int index, String str) {

        if (index == 0)
            tf1.setText(str);
        else if (index == 1)
            tf2.setText(str);
        else if (index == 2)
            tf3.setText(str);
        else if (index == 3)
            tf4.setText(str);
        else if (index == 4)
            tf5.setText(str);
        else if (index == 5)
            tf6.setText(str);
        else if (index == 6)
            tf7.setText(str);
        else if (index == 7)
            tf8.setText(str);
        else if (index == 8)
            tf9.setText(str);
        else if (index == 9) {
            tf10.setText(str);
        }
    }

    public void setSentence(String str) {

        String[] chars = str.split("");
        if (chars.length >= 4) {
            tf1.setText(chars[0]);
            tf2.setText(chars[1]);
            tf3.setText(chars[2]);
            tf4.setText(chars[3]);
        }
        if (chars.length >= 5) {
            tf5.setText(chars[4]);
        }
        if (chars.length >= 6) {
            tf6.setText(chars[5]);
        }
        if (chars.length >= 7) {
            tf7.setText(chars[6]);
        }
        if (chars.length >= 8) {
            tf8.setText(chars[7]);
        }
        if (chars.length >= 9) {
            tf9.setText(chars[8]);
        }
        if (chars.length == 10) {
            tf10.setText(chars[9]);
        }
    }

    private void listenToServer() {
        new Thread(() -> {
            while (player.getSocket().isConnected()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (!player.messagesReceived.isEmpty()) {
                    String message = player.readMessageFromBuffer();
                    System.out.println("Received message from buffer: " + message);
                    //chatMessagesObservableList.add(message);
                    //chatListView.setItems(chatMessagesObservableList);
                }
            }
        }).start();
    }


    @FXML
    public void SpinTheWheel(ActionEvent actionEvent) {
        WheelSection wheelSection = WheelSection.wheelSpin();
        wheelImageView.setRotate(Integer.parseInt(wheelSection.getName())*15);
        player.sendMessage("MESSAGE");
    }
}
