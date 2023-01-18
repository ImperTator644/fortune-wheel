package com.example.fortunewheel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class LetterController {

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
    public TextField sentence;

    @FXML
    public TextField singleLetter;
    @FXML
    public Label category;
    @FXML
    public Label hint_label;
    String[] data = {"MEXICO COUNTRY", "HEDWIG BIRD", "KUAKATA BEACH", "CANADA COUNTRY", "DOCTOR PROFESSION", "FOOTBALL GAME", "TEACHER MENTOR", "LEOPARD ANIMAL", "BICYCLE TRANSPORT", "SALMON FISH", "SPARROW BIRD", "PARROTS BIRD", "EAGLE BIRD", "TRAIN TRANSPORT", "SHIP TRANSPORT", "ENGINEER PROFESSION", "BANKER PROFESSION", "CRICKET GAME"};
    int random = new Random().nextInt(data.length);
    String word_hint = data[random];
    String[] split = word_hint.split(" ", 2);
    String word = split[0];
    String categ = split[1];
    int letter_size = word.length();

    public void initialize() {
        setHint();
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

        if (word.contains(lett)) {
            int index = 0;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (String.valueOf(c).equals(lett)) {
                    setLetter(index, Character.toString(c));
                }
                index++;
            }
        }
    }
    public void CheckInputSentence(){

        String senten = sentence.getText();

        if (word.equals(senten)){
            setSentence(senten);
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
    public void changeScene(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("gameView.fxml"));
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Wheel of fortune");
        window.setScene(new Scene(parent, 800, 650));
        window.show();
    }
}

