package com.fortunewheel.frontend;

import com.fortunewheel.backend.MainInterface;
import com.fortunewheel.backend.connection.PlayerMessageSender;
import com.fortunewheel.backend.connection.ServerListener;
import com.fortunewheel.backend.game.Functions;
import com.fortunewheel.backend.handlers.players.Player;
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
    public TextField word;
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
    @FXML Label playerMoney;
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

    public void setupRound(String wordAndCategory) {
        String[] split = wordAndCategory.split(" ", 2);
        currentWord = split[0];
        currentCategory = split[1];
        setRoundCategoryAndKey();
    }

    private void setRoundCategoryAndKey() {
        tf1.setVisible(true);
        tf2.setVisible(true);
        tf3.setVisible(true);
        tf4.setVisible(true);
        tf5.setVisible(true);
        tf6.setVisible(true);
        tf7.setVisible(true);
        tf8.setVisible(true);
        tf9.setVisible(true);
        tf10.setVisible(true);


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
        String lett = singleLetter.getText().trim();
        if (!lett.isBlank() && lett.length() == 1) {

            singleLetter.setStyle("-fx-background-color:  #cef2d6; -fx-border-color:  #3b3737; " +
                    "-fx-border-radius: 5px; -fx-background-radius: 5px");

            PlayerMessageSender.sendMessage(player, Functions.LETTER, lett.toUpperCase());
            singleLetter.clear();
        }
        else {
            singleLetter.setStyle("-fx-background-color: #e86b6b; -fx-border-color:  #3b3737; " +
                    "-fx-border-radius: 5px; -fx-background-radius: 5px");
        }
    }

    public void uncoverLetters(String lett){
        int index = 0;
        for (int i = 0; i < currentWord.length(); i++) {
            char c = currentWord.charAt(i);
            if (String.valueOf(c).equals(lett)) {
                setLetter(index, Character.toString(c));
            }
            index++;
        }
    }

    public void uncoverWord(String word){
        setWord(word);
    }

    public void CheckInputSentence() {
        String guessedWord = word.getText().trim();
        if (!guessedWord.isBlank()) {
            word.setStyle(  "-fx-background-color:  #cef2d6; " +
                            "-fx-border-color:  #3b3737; " +
                            "-fx-border-radius: 5px; -fx-background-radius: 5px");
            PlayerMessageSender.sendMessage(player, Functions.WORD, guessedWord.toUpperCase());
            word.clear();
        }
        else {
            word.setStyle("-fx-background-color: #e86b6b; -fx-border-color:  #3b3737; " +
                    "-fx-border-radius: 5px; -fx-background-radius: 5px");
        }
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

    public void setWord(String str) {

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

    public void setRoundNumber(String roundNumber) {
        this.roundNumber.setText("RUNDA: " + roundNumber);
    }

    public void setCurrentPlayer(String currentPlayerName) {
        this.roundPlayer.setText("Tura gracza: " + currentPlayerName);
    }

    public void changeWheelView(WheelSection wheelSection) {
        wheelImageView.setRotate(360 - Integer.parseInt(wheelSection.getName()) * 15);
    }

    public void blockFields() {
        singleLetter.setEditable(false);
        word.setEditable(false);
        spinTheWheelButton.setVisible(false);
        sentenceButton.setVisible(false);
        singleLetterButton.setVisible(false);
    }

    public void unblockFields() {
        singleLetter.setEditable(true);
        word.setEditable(true);
        spinTheWheelButton.setVisible(false);
        sentenceButton.setVisible(true);
        singleLetterButton.setVisible(true);
    }

    public void unblockSpin(){
        singleLetter.setEditable(false);
        word.setEditable(false);
        spinTheWheelButton.setVisible(true);
        sentenceButton.setVisible(false);
        singleLetterButton.setVisible(false);
    }

    public void changeChat(String message) {
        chatListView.getItems().add(message);
        int items = chatListView.getItems().size();
        chatListView.scrollTo(items);
    }

    public void endRound() {
        tf1.setText("");
        tf2.setText("");
        tf3.setText("");
        tf4.setText("");
        tf5.setText("");
        tf6.setText("");
        tf7.setText("");
        tf8.setText("");
        tf9.setText("");
        tf10.setText("");
    }

    public void updateMoney(String money) {
        playerMoney.setText(money);
    }
}
