package com.fortunewheel.backend.connection;

import com.fortunewheel.backend.game.Functions;

public class Message {
    private String username;
    private Functions function;
    private String message;

    public String getUsername() {
        return username;
    }

    public Functions getFunction() {
        return function;
    }

    public String getMessage() {
        return message;
    }

    private Message(String username, Functions function, String message) {
        this.username = username;
        this.function = function;
        this.message = message;
    }

    @Override
    public String toString() {
        return String.join(":", username, String.valueOf(function), message);
    }

    public static Message convertStringToMessage(String message) {
        Message convertedMessage;

        String[] receivedMessage = message.split(":");
        convertedMessage = new Message.Builder()
                .setUsername(receivedMessage[0])
                .setFunction(Functions.valueOf(receivedMessage[1]))
                .setMessage(receivedMessage[2])
                .build();

        return convertedMessage;
    }

    public static class Builder {
        private String username;
        private Functions function;
        private String message;

        public Builder() {}

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setFunction(Functions function) {
            this.function = function;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Message build() {
            return new Message(username, function, message);
        }
    }

    public static String getMessageToChat(Message message){
        if (message.getFunction() == Functions.INFO){
            return message.getMessage();
        }
        if (message.getFunction() == Functions.SPIN){
            return "spin the wheel";
        }
        else return " chuj";
    }
}
