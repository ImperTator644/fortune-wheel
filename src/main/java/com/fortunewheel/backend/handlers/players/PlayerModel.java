package com.fortunewheel.backend.handlers.players;


public class PlayerModel {
    private final String username;

    public String getUsername() {
        return username;
    }

    public PlayerModel(String username) {
        this.username = username;
    }
}
