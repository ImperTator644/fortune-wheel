package com.fortunewheel.backend.game;

import com.fortunewheel.backend.database.Categories;
import com.fortunewheel.backend.database.DBPuzzels;

import java.util.Random;

public class Round {
    private final String wordAndCategory;

    public Round() {
        String[] data = Categories.getAllCategories();
        int random = new Random().nextInt(data.length);
        this.wordAndCategory = DBPuzzels.getWordPuzzles(data[random]) + " " + data[random];
        System.out.println(wordAndCategory);
    }

    public String getWordAndCategory() {
        return wordAndCategory;
    }

    public String getWord() {
        return wordAndCategory.split(" ")[0];
    }

    @Override
    public String toString() {
        return "Round{" +
                "wordAndCategory='" + wordAndCategory + '\'' +
                '}';
    }
}
