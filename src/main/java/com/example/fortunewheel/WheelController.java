package com.example.fortunewheel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class WheelController {

    @FXML
    public ImageView wheelImageView;

    @FXML
    public void SpinTheWheel(ActionEvent actionEvent) {
        WheelSection wheelSection = WheelSection.wheelSpin();
        wheelImageView.setRotate(Integer.parseInt(wheelSection.getName())*15);
    }
}
