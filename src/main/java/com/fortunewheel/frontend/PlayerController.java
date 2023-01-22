package com.fortunewheel.frontend;

import com.fortunewheel.backend.MainInterface;
import com.fortunewheel.backend.connection.PlayerMessageSender;
import com.fortunewheel.backend.connection.ServerListener;
import com.fortunewheel.backend.game.Functions;
import com.fortunewheel.backend.handlers.players.Player;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

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
    public Label roundNumber;
    @FXML
    public Label roundPlayer;

    @FXML
    public Button spinTheWheelButton;
    @FXML
    public Button singleLetterButton;
    @FXML
    public Button sentenceButton;
    @FXML
    public Label category;


    @FXML
    public ImageView wheelImageView;
    public Player player;

    private String currentWord;
    private String currentCategory;

    @FXML
    public void initialize() {
    }

    public void initData(Player player) {
        System.out.println("Received player");
        this.player = player;
        this.blockFields();
        ServerListener.listenToServer(player, this);
        //listenToServer();
    }

    public void setupRound(String wordAndCategory){
        String[] split = wordAndCategory.split(" ", 2);
        currentWord = split[0];
        currentCategory = split[1];
        setRoundCategoryAndKey();
    }

    private void setRoundCategoryAndKey() {
        category.setText(currentCategory);
        int letter_size = currentWord.length();
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

        if (currentWord.contains(lett)) {
            success = true;
            int index = 0;
            for (int i = 0; i < currentWord.length(); i++) {
                char c = currentWord.charAt(i);
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
        if (currentWord.equals(senten)){
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

    @FXML
    public void SpinTheWheel(ActionEvent actionEvent) {
        PlayerMessageSender.sendMessage(player, Functions.SPIN, "spin the wheel for me");
    }

    public void setRoundNumber(String roundNumber){
        this.roundNumber.setText("RUNDA: " + roundNumber);
    }
    public void setCurrentPlayer(String currentPlayerName){
        this.roundPlayer.setText("Tura gracza: " + currentPlayerName);
    }

    public void changeWheelView(WheelSection wheelSection) {
        wheelImageView.setRotate(Integer.parseInt(wheelSection.getName())*15);
    }

    public void blockFields(){
        singleLetter.setEditable(false);
        sentence.setEditable(false);
        spinTheWheelButton.setVisible(false);
        sentenceButton.setVisible(false);
        singleLetterButton.setVisible(false);
    }

    public void unblockFields(){
        singleLetter.setEditable(true);
        sentence.setEditable(true);
        spinTheWheelButton.setVisible(true);
        sentenceButton.setVisible(true);
        singleLetterButton.setVisible(true);
    }

    public void changeChat(String message){
        chatListView.getItems().add(message);
    }
}
