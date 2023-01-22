package com.example.fortunewheel;

import back.game.Functions;
import javafx.application.Platform;

public class PlayerMessageProcessor {
    public static void processMessage(Message message, PlayerController controller) {
        Functions function = message.getFunction();
        switch (function) {
            case SPIN -> {
                WheelSection wheelSection = WheelSection.valueOf(message.getMessage());
                controller.changeWheelView(wheelSection);
            }
            case BLOCK -> controller.blockFields();
            case UNBLOCK -> controller.unblockFields();
            case SETUP_ROUND -> {
                Platform.runLater(()->controller.setupRound(message.getMessage()));
            }
        }
    }
}
