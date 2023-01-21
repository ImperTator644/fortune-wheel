package com.example.fortunewheel;

import back.game.Functions;

public class PlayerMessageProcessor {
    public static void processMessage(Message message, PlayerController controller) {
        Functions function = message.getFunction();
        switch (function) {
            case SPIN -> {
                WheelSection wheelSection = WheelSection.valueOf(message.getMessage());
                controller.changeWheelView(wheelSection);
            }
        }
    }
}
